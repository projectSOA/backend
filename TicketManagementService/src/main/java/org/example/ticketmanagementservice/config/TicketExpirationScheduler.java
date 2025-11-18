package org.example.ticketmanagementservice.config;

import org.example.ticketmanagementservice.services.TicketService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TicketExpirationScheduler {

    private final TicketService ticketService;

    public TicketExpirationScheduler(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Runs every day at midnight to expire tickets that are no longer valid.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void expireTicketsAtMidnight() {
        ticketService.expireTickets();
    }
}

