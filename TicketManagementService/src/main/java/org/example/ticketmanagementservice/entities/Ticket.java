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
    private UUID userId;          // (User Service)

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

    @Column(name = "qr_code", length = 512, nullable = false)
    private String qrCode;        // stored token/QR payload (string)

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}