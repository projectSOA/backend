package org.example.ticketmanagementservice.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentConfirmParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.currency:usd}")
    private String currency;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    /**
     * Convert BigDecimal amount to cents (Stripe uses cents)
     */
    public Long convertToCents(BigDecimal amount) {
        return amount.multiply(new BigDecimal("100")).longValue();
    }

    /**
     * Create a payment intent in Stripe
     *
     * @param amountInCents Amount in cents
     * @param currency Currency code (e.g., "usd")
     * @param idempotencyKey Idempotency key for safe retries
     * @param metadata http://localhost:8080Additional metadata
     * @param paymentMethodId Optional payment method ID (pm_xxx) from Stripe
     * @return Created PaymentIntent
     * @throws StripeException if creation fails
     */
    public PaymentIntent createPaymentIntent(
            Long amountInCents,
            String currency,
            String idempotencyKey,
            Map<String, String> metadata,
            String paymentMethodId) throws StripeException {

        PaymentIntentCreateParams.Builder paramsBuilder = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency(currency);

        // If payment method ID is provided, use it; otherwise use automatic payment methods
        if (paymentMethodId != null && !paymentMethodId.isEmpty()) {
            paramsBuilder.setPaymentMethod(paymentMethodId);
            // Set confirmation method to automatic if payment method is provided
            paramsBuilder.setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC);
            paramsBuilder.setConfirm(true); // Automatically confirm if payment method is provided
        } else {
            // Use automatic payment methods if no payment method ID is provided
            paramsBuilder.setAutomaticPaymentMethods(
                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                            .setEnabled(true)
                            .build()
            );
        }

        // Add metadata if provided
        if (metadata != null && !metadata.isEmpty()) {
            paramsBuilder.putAllMetadata(metadata);
        }

        PaymentIntentCreateParams params = paramsBuilder.build();

        RequestOptions requestOptions = RequestOptions.builder()
                .setIdempotencyKey(idempotencyKey)
                .build();

        // Create payment intent with idempotency key
        return PaymentIntent.create(params, requestOptions);
    }

    /**
     * Confirm a payment intent
     *
     * @param paymentIntentId Payment intent ID
     * @param paymentMethodId Optional payment method ID (pm_xxx) to attach to the payment intent
     * @return Confirmed PaymentIntent
     * @throws StripeException if confirmation fails
     */
    public PaymentIntent confirmPaymentIntent(String paymentIntentId, String paymentMethodId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        
        PaymentIntentConfirmParams.Builder paramsBuilder = PaymentIntentConfirmParams.builder();
        
        // If payment method ID is provided, attach it to the payment intent
        if (paymentMethodId != null && !paymentMethodId.isEmpty()) {
            paramsBuilder.setPaymentMethod(paymentMethodId);
        }

        return paymentIntent.confirm(paramsBuilder.build());
    }

    /**
     * Retrieve a payment intent by ID
     *
     * @param paymentIntentId Payment intent ID
     * @return PaymentIntent
     * @throws StripeException if retrieval fails
     */
    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
        return PaymentIntent.retrieve(paymentIntentId);
    }
}

