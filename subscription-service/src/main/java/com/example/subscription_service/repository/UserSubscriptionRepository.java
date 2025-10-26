package com.example.subscription_service.repository;


import com.example.subscription_service.entity.UserSubscription;
import com.example.subscription_service.enums.UserSubscriptionStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSubscriptionRepository {

    //Check if user has subscription
    boolean existsByUserId(UUID userId);

    //Find subscription by userId
    Optional<UserSubscription> findByUserId(UUID userId);

    //Delete subscription by userId
    @Modifying
    @Query("DELETE FROM UserSubscription us WHERE us.userId = :userId ")
    int deleteByUserId(@Param("userId") UUID userId);

    //Update status of subscription by userId
    @Modifying
    @Query("UPDATE UserSubscription us SET us.status = :status WHERE us.userId = :userId")
    int updateStatusByUserId(@Param("userId") UUID userId, @Param("status") UserSubscriptionStatus status);

    //Decrement ticket count
    @Modifying
    @Query("UPDATE UserSubscription us SET us.numberTicketsLeft = :numberOfTicketsLeft, us.lastUsed = CURRENT_TIMESTAMP, us.id = :id")
    int decrementTicketCount(@Param("id") UUID id);

    // Update end date (for renewal/extension)
    @Modifying
    @Query("UPDATE UserSubscription us SET us.endDate = :endDate WHERE us.userId = :userId")
    int updateEndDateByUserId(@Param("userId") UUID userId, @Param("endDate") LocalDate endDate);

    // Check if subscription is valid for a specific date
    @Query("SELECT us FROM UserSubscription us WHERE us.userId = :userId AND us.status = 'ACTIVE' AND us.startDate <= :date AND us.endDate >= :date")
    Optional<UserSubscription> findActiveSubscriptionByDate(@Param("userId") UUID userId, @Param("date") LocalDate date);
}
