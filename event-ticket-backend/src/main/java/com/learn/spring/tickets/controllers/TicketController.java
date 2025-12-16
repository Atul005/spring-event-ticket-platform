package com.learn.spring.tickets.controllers;

import com.learn.spring.tickets.config.JwtUtils;
import com.learn.spring.tickets.domain.DTOs.GetTicketResponseDTO;
import com.learn.spring.tickets.domain.DTOs.ListTicketResponseDTO;
import com.learn.spring.tickets.domain.entities.Ticket;
import com.learn.spring.tickets.mappers.TicketMapper;
import com.learn.spring.tickets.services.QRCodeService;
import com.learn.spring.tickets.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final QRCodeService qrCodeService;

    @GetMapping
    public Page<ListTicketResponseDTO> listTickets(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ){
        return ticketService.listTicketsForUser(JwtUtils.getUserID(jwt), pageable)
                .map(ticketMapper::toListTicketResponseDTO);

    }

    @GetMapping(path = "/{ticketId}")
    public ResponseEntity<GetTicketResponseDTO> getTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ){
        return ticketService.getTicketForUser(ticketId, JwtUtils.getUserID(jwt))
                .map(ticketMapper::toGetTicketResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQRCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ){
        byte[] qrCodeImage = qrCodeService.getQRCodeImageForUserAndTicket(ticketId, JwtUtils.getUserID(jwt));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(qrCodeImage.length);

        return ResponseEntity.ok().headers(httpHeaders).body(qrCodeImage);
    }






}
