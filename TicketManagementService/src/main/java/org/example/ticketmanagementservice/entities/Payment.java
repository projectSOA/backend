package org.example.ticketmanagementservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.ticketmanagementservice.entities.enums.PaymentMethod;
import org.example.ticketmanagementservice.entities.enums.PaymentPurpose;
import org.example.ticketmanagementservice.entities.enums.PaymentStatus;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_user", columnList = "user_id"),
        @Index(name = "idx_payment_tx", columnList = "transaction_id", unique = true)
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id", columnDefinition = "uuid", nullable = false)
    private UUID userId;

    @Column(name = "price", precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", length = 32, nullable = false)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16, nullable = false)
    private PaymentStatus status;

    @Column(name = "transaction_id", length = 128, nullable = false)
    private String transactionId;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_purpose", nullable = false)
    private PaymentPurpose paymentPurpose;

    @Column(name = "related_id", columnDefinition = "uuid")
    private UUID relatedId;

    @Column(name = "idempotency_key", length = 80, nullable = false)
    private String idempotencyKey;        // caller-provided, reused on retries

}