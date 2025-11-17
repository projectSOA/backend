package org.example.ticketmanagementservice.repositories;

import org.example.ticketmanagementservice.entities.Ticket;
import org.example.ticketmanagementservice.entities.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findByUserId(UUID userId);

    List<Ticket> findByUserIdAndStatus(UUID userId, TicketStatus status);

    List<Ticket> findByStatusAndExpiresAtBefore(TicketStatus status, LocalDateTime threshold);

    Optional<Ticket> findByPaymentId(UUID paymentId);
}