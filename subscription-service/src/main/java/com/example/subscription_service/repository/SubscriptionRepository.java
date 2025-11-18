package com.example.subscription_service.repository;

import com.example.subscription_service.entity.Subscription;
import com.example.subscription_service.enums.SubscriptionPlan;
import com.example.subscription_service.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    List<Subscription> findByPlan(SubscriptionPlan plan);
    List<Subscription> findByStatus(SubscriptionStatus status);
    Optional<Subscription> findByPlanAndStatus(SubscriptionPlan plan, SubscriptionStatus status);


    @Modifying
    @Query("UPDATE Subscription s SET s.status = :status WHERE s.id = :id")
    int updateStatus(@Param("id") UUID id, @Param("status") SubscriptionStatus status);
}
