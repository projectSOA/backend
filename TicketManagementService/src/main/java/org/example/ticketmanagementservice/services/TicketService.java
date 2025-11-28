package org.example.ticketmanagementservice.services;

import org.example.ticketmanagementservice.api.model.TicketPurchaseRequest;
import org.example.ticketmanagementservice.entities.Payment;
import org.example.ticketmanagementservice.entities.Ticket;
import org.example.ticketmanagementservice.entities.enums.TicketStatus;
import org.example.ticketmanagementservice.exception.ResourceNotFoundException;
import org.example.ticketmanagementservice.exception.TicketValidationException;
import org.example.ticketmanagementservice.mapper.TicketMapper;
import org.example.ticketmanagementservice.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import java.security.MessageDigest;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final String qrSecret;

    public TicketService(TicketRepository ticketRepository,
                         TicketMapper ticketMapper,
                         @Value("${ticket.qr.secret}") String qrSecret) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.qrSecret = qrSecret;
    }

    @Transactional
    public Ticket createTicket(Payment payment, TicketPurchaseRequest request) {
        Ticket ticket = ticketMapper.toEntity(request);
        ticket.setPayment(payment);
        ticket.setStatus(TicketStatus.CONFIRMED);
        ticket.setPurchaseDate(payment.getPaymentDate());
        ticket.setExpiresAt(calculateExpiry(payment.getPaymentDate()));
        ticket.setValidatedAt(null);




        Ticket tickett = ticketRepository.save(ticket);
        UUID ticketId = tickett.getId();

        String qrCode = generateQrCode(ticketId);
        // Persist QR on the persisted entity (tickett) not on the original transient instance
        tickett.setQrCode(qrCode);
        tickett.setQrCodeGeneratedAt(LocalDateTime.now());
        return ticketRepository.save(tickett);
    }

    @Transactional(readOnly = true)
    public Ticket getTicket(UUID ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));
    }

    @Transactional(readOnly = true)
    public List<Ticket> getTicketsByUser(UUID userId) {
        return ticketRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Ticket> getActiveTicketsByUser(UUID userId) {
        LocalDateTime now = LocalDateTime.now();
        return ticketRepository.findByUserIdAndStatus(userId, TicketStatus.CONFIRMED)
                .stream()
                .filter(ticket -> ticket.getExpiresAt().isAfter(now))
                .collect(Collectors.toList());
    }

    @Transactional
    public Ticket validateTicket(String qrCode) {
        UUID ticketId = getTicketIdFromQrCode(qrCode);

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));

        if (ticket.getStatus() == TicketStatus.VALIDATED) {
            throw new TicketValidationException("Ticket already validated");
        }

        if (ticket.getStatus() != TicketStatus.CONFIRMED) {
            throw new TicketValidationException("Ticket cannot be validated in status: " + ticket.getStatus());
        }

        if (!ticket.getExpiresAt().isAfter(LocalDateTime.now())) {
            ticket.setStatus(TicketStatus.EXPIRED);
            ticketRepository.save(ticket);
            throw new TicketValidationException("Ticket expired");
        }

        ticket.setStatus(TicketStatus.VALIDATED);
        ticket.setValidatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    private UUID getTicketIdFromQrCode(String qrCode) {
        try {
            // Step 1: Base64 decode
            byte[] decodedBytes = Base64.getDecoder().decode(qrCode);
            String combined = new String(decodedBytes, StandardCharsets.UTF_8);

            // Expected format: "<ticketId>:<signature>"
            String[] parts = combined.split(":", 2);
            if (parts.length != 2) {
                throw new TicketValidationException("Invalid QR code format");
            }

            String payload = parts[0];      // ticketId as string
            String signature = parts[1];    // provided signature

            // Step 2: Recalculate the signature and compare
            String expectedSignature = signPayload(payload);

            // Optional constant-time comparison
            boolean matches = MessageDigest.isEqual(
                    expectedSignature.getBytes(StandardCharsets.UTF_8),
                    signature.getBytes(StandardCharsets.UTF_8)
            );

            if (!matches) {
                throw new TicketValidationException("QR code is invalid or has been tampered with");
            }

            // Step 3: Parse UUID from payload
            return UUID.fromString(payload);
        } catch (IllegalArgumentException ex) {
            // Base64 or UUID parsing issues
            throw new TicketValidationException("Invalid QR code");
        } catch (TicketValidationException ex) {
            // Just rethrow our own exception type
            throw ex;
        } catch (Exception ex) {
            // Any other unexpected problem
            throw new TicketValidationException("Unable to read QR code");
        }
    }



    @Transactional
    public int expireTickets() {
        LocalDateTime now = LocalDateTime.now();
        List<Ticket> expiringTickets = ticketRepository.findByStatusAndExpiresAtBefore(TicketStatus.CONFIRMED, now);
        expiringTickets.forEach(ticket -> ticket.setStatus(TicketStatus.EXPIRED));
        ticketRepository.saveAll(expiringTickets);
        return expiringTickets.size();
    }

    @Transactional(readOnly = true)
    public Ticket getByPaymentId(UUID paymentId) {
        return ticketRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found for payment id: " + paymentId));
    }

    private String generateQrCode(UUID ticketId) {
        String payload = ticketId.toString();
        String signature = signPayload(payload);
        String combined = payload + ":" + signature;
        return Base64.getEncoder().encodeToString(combined.getBytes(StandardCharsets.UTF_8));
    }

    private String signPayload(String payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(qrSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] rawHmac = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to sign QR payload", e);
        }
    }

    private LocalDateTime calculateExpiry(LocalDateTime paymentDate) {
        LocalDate nextDay = paymentDate.toLocalDate().plusDays(1);
        return nextDay.atStartOfDay();
    }
}

