package com.learn.spring.tickets.controllers;

import com.learn.spring.tickets.domain.DTOs.TicketValidationRequestDTO;
import com.learn.spring.tickets.domain.DTOs.TicketValidationResponseDTO;
import com.learn.spring.tickets.domain.entities.TicketValidation;
import com.learn.spring.tickets.domain.entities.TicketValidationMethodEnum;
import com.learn.spring.tickets.mappers.TicketMapper;
import com.learn.spring.tickets.mappers.TicketValidationMapper;
import com.learn.spring.tickets.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;


    @PostMapping
    public ResponseEntity<TicketValidationResponseDTO> validateTicket(
            @RequestBody TicketValidationRequestDTO requestDTO
            ){

        TicketValidationMethodEnum validationMethod = requestDTO.getMethod();

        TicketValidation ticketValidation;

        if(TicketValidationMethodEnum.QR_SCAN.equals(validationMethod)){
            ticketValidation = ticketValidationService.validateTicketByQRCode(requestDTO.getId());
        } else {
            ticketValidation = ticketValidationService.validateTicketManually(requestDTO.getId());
        }
        return ResponseEntity.ok(ticketValidationMapper.toDTO(ticketValidation));

    }






}
