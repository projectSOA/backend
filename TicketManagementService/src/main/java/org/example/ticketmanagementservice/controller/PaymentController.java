package org.example.ticketmanagementservice.controller;

import com.stripe.exception.StripeException;
// Generated OpenAPI classes (available after mvn clean install)
import org.example.ticketmanagementservice.api.PaymentsApi;
import org.example.ticketmanagementservice.api.model.PaymentResponse;
import org.example.ticketmanagementservice.api.model.SubscriptionPurchaseRequest;
import org.example.ticketmanagementservice.api.model.TicketPurchaseRequest;
import org.example.ticketmanagementservice.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Payment Controller implementing the OpenAPI generated interface
 * 
 * After running 'mvn clean install', the OpenAPI generator will create:
 * - org.example.ticketmanagementservice.api.PaymentsApi (interface)
 * - org.example.ticketmanagementservice.api.model.* (DTOs)
 * 
 * This controller implements PaymentsApi and delegates to PaymentService
 */
@RestController
public class PaymentController implements PaymentsApi {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public ResponseEntity<PaymentResponse> processTicketPurchase(TicketPurchaseRequest ticketPurchaseRequest) {
        PaymentResponse response = null;
        try {
            response = paymentService.processTicketPurchase(ticketPurchaseRequest);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PaymentResponse> processSubscriptionPurchase(SubscriptionPurchaseRequest subscriptionPurchaseRequest) {
        PaymentResponse response = null;
        try {
            response = paymentService.processSubscriptionPurchase(subscriptionPurchaseRequest);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PaymentResponse> getPaymentById(UUID paymentId) {
        PaymentResponse response = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PaymentResponse> getPaymentByTransactionId(String transactionId) {
        PaymentResponse response = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(response);
    }
}
