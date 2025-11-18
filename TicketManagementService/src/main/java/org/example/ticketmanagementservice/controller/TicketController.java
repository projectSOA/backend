package org.example.ticketmanagementservice.controller;

import org.example.ticketmanagementservice.api.model.TicketResponse;
import org.example.ticketmanagementservice.mapper.TicketApiMapper;
import org.example.ticketmanagementservice.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketApiMapper ticketApiMapper;

    public TicketController(TicketService ticketService, TicketApiMapper ticketApiMapper) {
        this.ticketService = ticketService;
        this.ticketApiMapper = ticketApiMapper;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponse> getTicket(@PathVariable UUID ticketId) {
        return ResponseEntity.ok(ticketApiMapper.toResponse(ticketService.getTicket(ticketId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(ticketApiMapper.toResponseList(ticketService.getTicketsByUser(userId)));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<TicketResponse>> getActiveTicketsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(ticketApiMapper.toResponseList(ticketService.getActiveTicketsByUser(userId)));
    }

    @PostMapping("/{ticketId}/validate")
    public ResponseEntity<TicketResponse> validateTicket(@PathVariable UUID ticketId) {
        return ResponseEntity.ok(ticketApiMapper.toResponse(ticketService.validateTicket(ticketId)));
    }
}

