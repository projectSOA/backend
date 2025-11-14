package com.example.subscription_service.repository;

import com.example.subscription_service.entity.UserSubscription;
import com.example.subscription_service.enums.UserSubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UUID> {

    boolean existsByUserId(UUID userId);


    Optional<UserSubscription> findByUserId(UUID userId);

    List<UserSubscription> findAllByUserId(UUID userId);

    @Modifying
    @Query("DELETE FROM UserSubscription us WHERE us.userId = :userId")
    int deleteByUserId(@Param("userId") UUID userId);


    @Query("SELECT us FROM UserSubscription us " +
            "WHERE us.userId = :userId AND us.status = 'ACTIVE' " +
            "AND us.startDate <= :date AND us.endDate >= :date")
    Optional<UserSubscription> findActiveSubscriptionByDate(@Param("userId") UUID userId, @Param("date") LocalDate date);

}
