package com.yourservice.service;

import com.yourservice.client.payment.api.PaymentsApi;
import com.yourservice.client.payment.api.ApiClient;
import com.yourservice.client.payment.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Example Payment Client Service
 * 
 * This is a template that other services can use to integrate with the Payment Service.
 * 
 * Steps to use:
 * 1. Copy payment-api.yaml to your service's src/main/resources/openapi/
 * 2. Add OpenAPI Generator plugin to your pom.xml (see example-client-pom.xml)
 * 3. Run 'mvn clean install' to generate client DTOs
 * 4. Copy this class and update the package name
 * 5. Configure payment.service.url in your application.properties
 */
@Service
public class PaymentClientService {
    
    private final PaymentsApi paymentsApi;
    
    @Value("${payment.service.url:http://localhost:8080}")
    private String paymentServiceUrl;
    
    public PaymentClientService() {
        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        
        // Create API client
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(paymentServiceUrl);
        
        // Create PaymentsApi instance
        this.paymentsApi = new PaymentsApi(apiClient);
    }
    
    /**
     * Process ticket purchase payment
     * 
     * @param userId User ID
     * @param routeId Route ID
     * @param startStopId Starting stop ID
     * @param endStopId Ending stop ID
     * @param price Ticket price
     * @param paymentMethod Payment method (CREDIT_CARD, DEBIT_CARD, etc.)
     * @param idempotencyKey Unique key for idempotency (optional, will be generated if null)
     * @return PaymentResponse with payment details
     */
    public PaymentResponse processTicketPurchase(
            UUID userId,
            UUID routeId,
            UUID startStopId,
            UUID endStopId,
            java.math.BigDecimal price,
            String paymentMethod,
            String idempotencyKey) {
        
        try {
            // Create request DTO using generated class
            TicketPurchaseRequest request = new TicketPurchaseRequest();
            request.setUserId(userId);
            request.setRouteId(routeId);
            request.setStartStopId(startStopId);
            request.setEndStopId(endStopId);
            request.setPrice(price);
            request.setPaymentMethod(
                TicketPurchaseRequest.PaymentMethodEnum.fromValue(paymentMethod)
            );
            request.setIdempotencyKey(
                idempotencyKey != null ? idempotencyKey : UUID.randomUUID().toString()
            );
            
            // Call the payment service API
            ResponseEntity<PaymentResponse> response = paymentsApi.processTicketPurchase(request);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Payment processing failed with status: " + response.getStatusCode());
            }
        } catch (com.yourservice.client.payment.ApiException e) {
            throw new RuntimeException("Payment API error: " + e.getMessage(), e);
        }
    }
    
    /**
     * Process subscription purchase payment
     * 
     * @param userId User ID
     * @param subscriptionId Subscription ID
     * @param price Subscription price
     * @param paymentMethod Payment method (CREDIT_CARD, DEBIT_CARD, etc.)
     * @param idempotencyKey Unique key for idempotency (optional)
     * @return PaymentResponse with payment details
     */
    public PaymentResponse processSubscriptionPurchase(
            UUID userId,
            UUID subscriptionId,
            java.math.BigDecimal price,
            String paymentMethod,
            String idempotencyKey) {
        
        try {
            // Create request DTO using generated class
            SubscriptionPurchaseRequest request = new SubscriptionPurchaseRequest();
            request.setUserId(userId);
            request.setSubscriptionId(subscriptionId);
            request.setPrice(price);
            request.setMethod(
                SubscriptionPurchaseRequest.MethodEnum.fromValue(paymentMethod)
            );
            request.setIdempotencyKey(
                idempotencyKey != null ? idempotencyKey : UUID.randomUUID().toString()
            );
            
            // Call the payment service API
            ResponseEntity<PaymentResponse> response = paymentsApi.processSubscriptionPurchase(request);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Payment processing failed with status: " + response.getStatusCode());
            }
        } catch (com.yourservice.client.payment.ApiException e) {
            throw new RuntimeException("Payment API error: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get payment by ID
     * 
     * @param paymentId Payment ID
     * @return PaymentResponse with payment details
     */
    public PaymentResponse getPaymentById(UUID paymentId) {
        try {
            ResponseEntity<PaymentResponse> response = paymentsApi.getPaymentById(paymentId);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Payment not found");
            }
        } catch (com.yourservice.client.payment.ApiException e) {
            if (e.getCode() == 404) {
                throw new RuntimeException("Payment not found with id: " + paymentId);
            }
            throw new RuntimeException("Payment API error: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get payment by Stripe transaction ID
     * 
     * @param transactionId Stripe transaction ID
     * @return PaymentResponse with payment details
     */
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        try {
            ResponseEntity<PaymentResponse> response = paymentsApi.getPaymentByTransactionId(transactionId);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Payment not found");
            }
        } catch (com.yourservice.client.payment.ApiException e) {
            if (e.getCode() == 404) {
                throw new RuntimeException("Payment not found with transaction id: " + transactionId);
            }
            throw new RuntimeException("Payment API error: " + e.getMessage(), e);
        }
    }
}

