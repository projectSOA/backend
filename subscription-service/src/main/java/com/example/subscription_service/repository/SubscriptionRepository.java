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

    // ========== FIND OPERATIONS ==========

    // Find by plan
    List<Subscription> findByPlan(SubscriptionPlan plan);

    // Find by status
    List<Subscription> findByStatus(SubscriptionStatus status);

    // Find active subscriptions with auto-renewal
    List<Subscription> findByStatusAndAutoRenewalTrue(SubscriptionStatus status);

    // Find by plan and status
    Optional<Subscription> findByPlanAndStatus(SubscriptionPlan plan, SubscriptionStatus status);

    // Update subscription status
    @Modifying
    @Query("UPDATE Subscription s SET s.status = :status WHERE s.id = :id")
    int updateStatus(@Param("id") UUID id, @Param("status") SubscriptionStatus status);

    // Update subscription price
    @Modifying
    @Query("UPDATE Subscription s SET s.price = :price WHERE s.id = :id")
    int updatePrice(@Param("id") UUID id, @Param("price") BigDecimal price);

    // Update total tickets
    @Modifying
    @Query("UPDATE Subscription s SET s.totalTickets = :totalTickets WHERE s.id = :id")
    int updateTotalTickets(@Param("id") UUID id, @Param("totalTickets") Integer totalTickets);

    // Update validity days
    @Modifying
    @Query("UPDATE Subscription s SET s.validityDays = :validityDays WHERE s.id = :id")
    int updateValidityDays(@Param("id") UUID id, @Param("validityDays") Integer validityDays);

    // Update description
    @Modifying
    @Query("UPDATE Subscription s SET s.description = :description WHERE s.id = :id")
    int updateDescription(@Param("id") UUID id, @Param("description") String description);

    // Update multiple fields at once
    @Modifying
    @Query("UPDATE Subscription s SET s.price = :price, s.totalTickets = :totalTickets, s.validityDays = :validityDays WHERE s.id = :id")
    int updateSubscriptionDetails(@Param("id") UUID id,
                                  @Param("price") BigDecimal price,
                                  @Param("totalTickets") Integer totalTickets,
                                  @Param("validityDays") Integer validityDays);

    // Delete by plan
    @Modifying
    @Query("DELETE FROM Subscription s WHERE s.plan = :plan")
    int deleteByPlan(@Param("plan") SubscriptionPlan plan);

    // Delete by status
    @Modifying
    @Query("DELETE FROM Subscription s WHERE s.status = :status")
    int deleteByStatus(@Param("status") SubscriptionStatus status);
}