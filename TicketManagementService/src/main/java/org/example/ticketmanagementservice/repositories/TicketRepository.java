package org.example.ticketmanagementservice.repositories;

import org.example.ticketmanagementservice.entities.Ticket;
import org.example.ticketmanagementservice.entities.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findByUserId(UUID userId);

    List<Ticket> findByUserIdAndStatus(UUID userId, TicketStatus status);

    List<Ticket> findByStatusAndExpiresAtBefore(TicketStatus status, LocalDateTime threshold);

    Optional<Ticket> findByPaymentId(UUID paymentId);


    @Query(value = "select t from Ticket  t where MONTH(t.createdAt)=:month And year(t.createdAt)=:year")
    List<Ticket> fetchTicketsSoldInAMonthInAYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT t FROM Ticket t WHERE t.createdAt >= :start AND t.createdAt < :end")
    List<Ticket> fetchTicketsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("Select count(t), FUNCTION('DAYOFWEEK', t.createdAt) from Ticket  t where t.createdAt>=:start and t.createdAt<:end group by FUNCTION('DAYOFWEEK', t.createdAt) order by FUNCTION('DAYOFWEEK', t.createdAt)")
    List<Object[]> fetchTicketsCountByDayBetweenStartAndEnd(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}