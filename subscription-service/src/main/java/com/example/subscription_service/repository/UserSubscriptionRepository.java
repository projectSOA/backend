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

    @Query("""
SELECT COUNT(us) 
FROM UserSubscription us
WHERE YEAR(us.startDate) = YEAR(CURRENT_DATE)
  AND MONTH(us.startDate) = MONTH(CURRENT_DATE)
""")
    long countSubscriptionsThisMonth();

    @Query("""
SELECT COUNT(us) 
FROM UserSubscription us
WHERE YEAR(us.startDate) = YEAR(CURRENT_DATE - 1 MONTH)
  AND MONTH(us.startDate) = MONTH(CURRENT_DATE - 1 MONTH)
""")
    long countSubscriptionsLastMonth();

    @Query("""
SELECT 
  YEAR(us.startDate) AS yr,
  MONTH(us.startDate) AS mn,
  COUNT(us) AS total
FROM UserSubscription us
WHERE us.startDate >= :startDate
GROUP BY YEAR(us.startDate), MONTH(us.startDate)
ORDER BY YEAR(us.startDate) DESC, MONTH(us.startDate) DESC
""")
    List<Object[]> countSubscriptionsByMonthSince(@Param("startDate") LocalDate startDate);


    @Query("""
SELECT COUNT(us) 
FROM UserSubscription us
WHERE us.startDate >= :start AND us.startDate < :end
""")
    long countBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);


}
