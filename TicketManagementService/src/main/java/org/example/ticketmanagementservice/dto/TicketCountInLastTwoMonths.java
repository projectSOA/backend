package org.example.ticketmanagementservice.dto;

public record TicketCountInLastTwoMonths(
        long numberOfTicketsSoldThisMonth,
        long numberOfTicketsSoldLastMonth
) {
}
