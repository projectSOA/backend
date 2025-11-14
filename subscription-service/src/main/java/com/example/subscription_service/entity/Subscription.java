package com.example.subscription_service.entity;

import com.example.subscription_service.enums.SubscriptionPlan;
import com.example.subscription_service.enums.SubscriptionStatus;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.math.BigDecimal;

@Table(name = "subscription")
@Getter
@Setter
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan", nullable = false)
    private SubscriptionPlan plan;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatus status;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "total_tickets", nullable = false)
    private Integer totalTickets;

    @Column(name = "validity_days", nullable = false)
    private Integer validityDays;

    @Column(name = "auto_renewal", nullable = false)
    private Boolean autoRenewal;

    @Column(name = "description")
    private String description;
}