package com.learn.spring.tickets.services;

import com.learn.spring.tickets.domain.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {

    TicketValidation validateTicketByQRCode(UUID qrCodeId);
    TicketValidation validateTicketManually(UUID ticketId);

}
