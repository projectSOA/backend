package org.example.ticketmanagementservice.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
// Generated OpenAPI classes (available after mvn clean install)
import org.example.ticketmanagementservice.api.model.PaymentResponse;
import org.example.ticketmanagementservice.api.model.SubscriptionPurchaseRequest;
import org.example.ticketmanagementservice.api.model.TicketPurchaseRequest;
import org.example.ticketmanagementservice.api.model.TicketPurchaseResponse;
import org.example.ticketmanagementservice.entities.Payment;
import org.example.ticketmanagementservice.entities.Ticket;
import org.example.ticketmanagementservice.entities.enums.PaymentPurpose;
import org.example.ticketmanagementservice.entities.enums.PaymentStatus;
import org.example.ticketmanagementservice.exception.ResourceNotFoundException;
import org.example.ticketmanagementservice.mapper.PaymentMapper;
import org.example.ticketmanagementservice.mapper.SubscriptionPurchaseToPaymentMapper;
import org.example.ticketmanagementservice.mapper.TicketPurchaseToPaymentMapper;
import org.example.ticketmanagementservice.mapper.TicketApiMapper;
import org.example.ticketmanagementservice.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {

    private final StripeService stripeService;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final TicketPurchaseToPaymentMapper ticketPurchaseToPaymentMapper;
    private final SubscriptionPurchaseToPaymentMapper subscriptionPurchaseToPaymentMapper;
    private final TicketService ticketService;
    private final TicketApiMapper ticketApiMapper;
    private final RestClient subscriptionServiceClient;

    @Value("${stripe.currency:usd}")
    private String currency;

    public PaymentService(
            StripeService stripeService,
            PaymentRepository paymentRepository,
            PaymentMapper paymentMapper,
            TicketPurchaseToPaymentMapper ticketPurchaseToPaymentMapper,
            SubscriptionPurchaseToPaymentMapper subscriptionPurchaseToPaymentMapper,
            TicketService ticketService,
            TicketApiMapper ticketApiMapper,
            @Qualifier("subscriptionServiceClient") RestClient subscriptionServiceClient) {
        this.stripeService = stripeService;
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.ticketPurchaseToPaymentMapper = ticketPurchaseToPaymentMapper;
        this.subscriptionPurchaseToPaymentMapper = subscriptionPurchaseToPaymentMapper;
        this.ticketService = ticketService;
        this.ticketApiMapper = ticketApiMapper;
        this.subscriptionServiceClient = subscriptionServiceClient;
    }

    /**
     * Process ticket purchase payment
     * 
     * @param request Ticket purchase request (generated OpenAPI DTO)
     * @return PaymentResponse with payment details
     * @throws StripeException if payment processing fails
     */
    @Transactional
    public TicketPurchaseResponse processTicketPurchase(TicketPurchaseRequest request) throws StripeException {
        // Generate or use provided idempotency key
        String idempotencyKey = (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isEmpty())
            ? request.getIdempotencyKey()
            : UUID.randomUUID().toString();

        // Check for idempotency
        var existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey);
        if (existingPayment.isPresent()) {
            Payment existing = existingPayment.get();
            if (existing.getStatus() != PaymentStatus.COMPLETED) {
                throw new IllegalStateException("Payment not completed yet. Current status: " + existing.getStatus());
            }
            Ticket ticket = ticketService.getByPaymentId(existing.getId());
            return buildTicketPurchaseResponse(paymentMapper.toPaymentResponse(existing), ticket);
        }

        // Map to Payment entity
        Payment payment = ticketPurchaseToPaymentMapper.toPayment(request);
        payment.setPaymentPurpose(PaymentPurpose.TICKET);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setIdempotencyKey(idempotencyKey); // Ensure idempotency key is always set

        // Create Stripe payment intent
        Long amountInCents = stripeService.convertToCents(request.getPrice());
        Map<String, String> metadata = new HashMap<>();
        metadata.put("user_id", request.getUserId().toString());
        metadata.put("route_id", request.getRouteId().toString());
        metadata.put("purpose", "TICKET");

        // Get payment method ID from request (if provided)
        String paymentMethodId = request.getPaymentMethodId();

        PaymentIntent paymentIntent = stripeService.createPaymentIntent(
                amountInCents,
                currency,
                idempotencyKey,
                metadata,
                paymentMethodId
        );

        // Update payment with Stripe transaction ID
        payment.setTransactionId(paymentIntent.getId());

        // Save payment
        Payment savedPayment = paymentRepository.save(payment);

        // Handle payment intent status
        // If paymentMethodId was provided, the intent was already confirmed during creation
        // If not, we need to confirm it separately
        try {
            PaymentIntent confirmedIntent = paymentIntent;
            
            // If payment method was not provided, confirm the payment intent now
            if (paymentMethodId == null || paymentMethodId.isEmpty()) {
                confirmedIntent = stripeService.confirmPaymentIntent(paymentIntent.getId(), null);
            }
            // If payment method was provided, the intent is already confirmed (or requires action)
            // Just check the status
            
            // Update payment status based on Stripe payment intent status
            if ("succeeded".equals(confirmedIntent.getStatus())) {
                savedPayment.setStatus(PaymentStatus.COMPLETED);
            } else if ("requires_action".equals(confirmedIntent.getStatus()) || 
                       "requires_confirmation".equals(confirmedIntent.getStatus())) {
                savedPayment.setStatus(PaymentStatus.PENDING);
            } else {
                savedPayment.setStatus(PaymentStatus.FAILED);
            }
            savedPayment = paymentRepository.save(savedPayment);
        } catch (StripeException e) {
            savedPayment.setStatus(PaymentStatus.FAILED);
            savedPayment = paymentRepository.save(savedPayment);
            throw e;
        }

        if (savedPayment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Payment not completed yet. Current status: " + savedPayment.getStatus());
        }

        Ticket ticket = ticketService.createTicket(savedPayment, request);
        PaymentResponse paymentResponse = paymentMapper.toPaymentResponse(savedPayment);
        return buildTicketPurchaseResponse(paymentResponse, ticket);
    }
    private TicketPurchaseResponse buildTicketPurchaseResponse(PaymentResponse paymentResponse, Ticket ticket) {
        TicketPurchaseResponse response = new TicketPurchaseResponse();
        response.setPayment(paymentResponse);
        response.setTicket(ticketApiMapper.toResponse(ticket));
        return response;
    }


    /**
     * Process subscription purchase payment
     * 
     * @param request Subscription purchase request (generated OpenAPI DTO)
     * @return PaymentResponse with payment details
     * @throws StripeException if payment processing fails
     */
    @Transactional
    public PaymentResponse processSubscriptionPurchase(SubscriptionPurchaseRequest request) throws StripeException {
        // Generate or use provided idempotency key
        String idempotencyKey = (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isEmpty())
            ? request.getIdempotencyKey()
            : UUID.randomUUID().toString();

        // Check for idempotency
        var existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey);
        if (existingPayment.isPresent()) {
            return paymentMapper.toPaymentResponse(existingPayment.get());
        }

        // Map to Payment entity
        Payment payment = subscriptionPurchaseToPaymentMapper.toPayment(request);
        payment.setPaymentPurpose(PaymentPurpose.SUBSCRIPTION);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setIdempotencyKey(idempotencyKey); // Ensure idempotency key is always set

        // Create Stripe payment intent
        Long amountInCents = stripeService.convertToCents(request.getPrice());
        Map<String, String> metadata = new HashMap<>();
        metadata.put("user_id", request.getUserId().toString());
        metadata.put("subscription_id", request.getSubscriptionId().toString());
        metadata.put("purpose", "SUBSCRIPTION");

        // Get payment method ID from request (if provided)
        String paymentMethodId = request.getPaymentMethodId();

        PaymentIntent paymentIntent = stripeService.createPaymentIntent(
                amountInCents,
                currency,
                idempotencyKey,
                metadata,
                paymentMethodId
        );

        // Update payment with Stripe transaction ID
        payment.setTransactionId(paymentIntent.getId());

        // Save payment
        Payment savedPayment = paymentRepository.save(payment);

        // Handle payment intent status
        // If paymentMethodId was provided, the intent was already confirmed during creation
        // If not, we need to confirm it separately
        try {
            PaymentIntent confirmedIntent = paymentIntent;
            
            // If payment method was not provided, confirm the payment intent now
            if (paymentMethodId == null || paymentMethodId.isEmpty()) {
                confirmedIntent = stripeService.confirmPaymentIntent(paymentIntent.getId(), null);
            }
            // If payment method was provided, the intent is already confirmed (or requires action)
            // Just check the status
            
            // Update payment status based on Stripe payment intent status
            if ("succeeded".equals(confirmedIntent.getStatus())) {
                savedPayment.setStatus(PaymentStatus.COMPLETED);
            } else if ("requires_action".equals(confirmedIntent.getStatus()) || 
                       "requires_confirmation".equals(confirmedIntent.getStatus())) {
                savedPayment.setStatus(PaymentStatus.PENDING);
            } else {
                savedPayment.setStatus(PaymentStatus.FAILED);
            }
            savedPayment = paymentRepository.save(savedPayment);
        } catch (StripeException e) {
            savedPayment.setStatus(PaymentStatus.FAILED);
            savedPayment = paymentRepository.save(savedPayment);
            throw e;
        }

        return paymentMapper.toPaymentResponse(savedPayment);
    }

    private void createUserSubscription(UUID userId, UUID subscriptionId) {
        try {
            Map<String, UUID> requestBody = new HashMap<>();
            requestBody.put("userId", userId);
            requestBody.put("subscriptionId", subscriptionId);

            subscriptionServiceClient.post()
                    .uri("/user-subscription")
                    .body(requestBody)
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating user subscription: " + e.getMessage(), e);
        }
    }


    /**
     * Get payment by ID
     * 
     * @param paymentId Payment ID
     * @return PaymentResponse
     */
    public PaymentResponse getPaymentById(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));
        return paymentMapper.toPaymentResponse(payment);
    }

    /**
     * Get payment by transaction ID
     * 
     * @param transactionId Stripe transaction ID
     * @return PaymentResponse
     */
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findAll().stream()
                .filter(p -> p.getTransactionId().equals(transactionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with transaction id: " + transactionId));
        return paymentMapper.toPaymentResponse(payment);
    }
}

