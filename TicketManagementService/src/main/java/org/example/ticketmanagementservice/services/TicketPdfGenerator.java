package org.example.ticketmanagementservice.services;


import org.example.ticketmanagementservice.entities.Ticket;
import org.springframework.stereotype.Service;

@Service
public class TicketPdfGenerator {

    /**
     * Generate a simple "PDF" as bytes for the given ticket.
     * Right now it's just plain text bytes – you can replace this
     * later with a real PDF library (iText, OpenPDF, etc.).
     */
    public byte[] generateTicketPdf(Ticket ticket) {
        String content = """
                TICKET DETAILS
                --------------
                Ticket ID   : %s
                User ID     : %s
                Route ID    : %s
                From Stop   : %s
                To Stop     : %s
                Price       : %s
                Status      : %s
                Purchase    : %s
                Expires At  : %s
                """.formatted(
                ticket.getId(),
                ticket.getUserId(),
                ticket.getRouteId(),
                ticket.getStartStopId(),
                ticket.getEndStopId(),
                ticket.getPrice(),
                ticket.getStatus(),
                ticket.getPurchaseDate(),
                ticket.getExpiresAt()
        );

        // Just return text bytes for now (placeholder)
        return content.getBytes();
    }
}

