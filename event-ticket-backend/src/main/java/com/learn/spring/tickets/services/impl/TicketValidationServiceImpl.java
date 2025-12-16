package com.learn.spring.tickets.services.impl;

import com.learn.spring.tickets.domain.entities.*;
import com.learn.spring.tickets.exceptions.QRCodeNotFoundException;
import com.learn.spring.tickets.exceptions.TicketNotFoundException;
import com.learn.spring.tickets.repositories.QRCodeRepository;
import com.learn.spring.tickets.repositories.TicketRepository;
import com.learn.spring.tickets.repositories.TicketValidationRepository;
import com.learn.spring.tickets.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QRCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQRCode(UUID qrCodeId) {
        QRCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QRCodeStatusEnum.ACTIVE).orElseThrow(
                () -> new QRCodeNotFoundException(
                        String.format("QR Code with id '%s' not found", qrCodeId)
                )
        );
        Ticket ticket = qrCode.getTicket();
        return getTicketValidation(ticket, TicketValidationMethodEnum.QR_SCAN);
    }


    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        return getTicketValidation(ticket, TicketValidationMethodEnum.MANUAL);
    }

    private TicketValidation getTicketValidation(Ticket ticket, TicketValidationMethodEnum method) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(method);

//        TicketValidationStatusEnum ticketValidationStatusEnum = ticket.getValidations()
//                .stream()
//                .filter(tikVal -> TicketValidationStatusEnum.VALID.equals((tikVal.getStatus())))
//                .findFirst()
//                .map(tv -> TicketValidationStatusEnum.INVALID)
//                .orElse(TicketValidationStatusEnum.VALID);


        boolean isValidated = ticket.getValidations().stream()
                .anyMatch(v -> v.getStatus() == TicketValidationStatusEnum.VALID);

        TicketValidationStatusEnum validationStatus = isValidated
                ? TicketValidationStatusEnum.INVALID
                : TicketValidationStatusEnum.VALID;

        ticketValidation.setStatus(validationStatus);
        return ticketValidationRepository.save(ticketValidation);
    }


}
