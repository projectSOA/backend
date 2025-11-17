package org.example.ticketmanagementservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.ticketmanagementservice.entities.enums.TicketStatus;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets", indexes = {
        @Index(name = "idx_ticket_user", columnList = "user_id"),
        @Index(name = "idx_ticket_route", columnList = "route_id"),
        @Index(name = "idx_ticket_start_stop", columnList = "start_stop_id"),
        @Index(name = "idx_ticket_end_stop", columnList = "end_stop_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id", columnDefinition = "uuid", nullable = false)
    private UUID userId;          // purchaser identifier

    @Column(name = "route_id", columnDefinition = "uuid", nullable = false)
    private UUID routeId;         // (Route Service)

    @Column(name = "start_stop_id", columnDefinition = "uuid", nullable = false)
    private UUID startStopId;     // (Route Service)

    @Column(name = "end_stop_id", columnDefinition = "uuid", nullable = false)
    private UUID endStopId;       // (Route Service)

    @Column(name = "price", precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16, nullable = false)
    private TicketStatus status;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

    @Column(name = "qr_code", length = 512)
    private String qrCode;        // stored token/QR payload (string)

    @Column(name = "qr_code_generated_at")
    private LocalDateTime qrCodeGeneratedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}