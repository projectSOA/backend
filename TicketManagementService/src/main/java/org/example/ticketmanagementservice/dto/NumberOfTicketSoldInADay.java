package org.example.ticketmanagementservice.dto;

public record NumberOfTicketSoldInADay (
        int day,
        long numberOfTickets
){
}
