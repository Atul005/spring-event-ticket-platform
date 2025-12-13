package com.learn.spring.tickets.controllers;


import com.learn.spring.tickets.config.JwtUtils;
import com.learn.spring.tickets.domain.entities.Ticket;
import com.learn.spring.tickets.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/events/{eventId}/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;



    @PostMapping(path = "/{ticketTypeId}/tickets")
    public ResponseEntity<Void> purchaseTickets(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketTypeId
            ){
        UUID userID = JwtUtils.getUserID(jwt);
        ticketTypeService.purchaseTicket(userID, ticketTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
