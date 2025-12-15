package com.learn.spring.tickets.services;

import com.learn.spring.tickets.domain.entities.QRCode;
import com.learn.spring.tickets.domain.entities.Ticket;

import java.util.UUID;

public interface QRCodeService {

    QRCode generateQRCode(Ticket ticket);

    byte[] getQRCodeImageForUserAndTicket(UUID ticketId, UUID userId);

}
