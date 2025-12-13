package com.learn.spring.tickets.services.impl;

import com.learn.spring.tickets.domain.entities.Ticket;
import com.learn.spring.tickets.domain.entities.TicketStatusEnum;
import com.learn.spring.tickets.domain.entities.TicketType;
import com.learn.spring.tickets.domain.entities.User;
import com.learn.spring.tickets.exceptions.TicketTypeNotFoundException;
import com.learn.spring.tickets.exceptions.TicketsNotAvailableException;
import com.learn.spring.tickets.exceptions.UserNotFoundException;
import com.learn.spring.tickets.repositories.TicketRepository;
import com.learn.spring.tickets.repositories.TicketTypeRepository;
import com.learn.spring.tickets.repositories.UserRepository;
import com.learn.spring.tickets.services.QRCodeService;
import com.learn.spring.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final UserRepository userRepository;
    private final QRCodeService qrCodeService;


    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' not found", userId)));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException(String.format("Ticket Type with id '%s' not found", ticketTypeId)));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketTypeId);
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable){
            throw new TicketsNotAvailableException("No Ticket Available");
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQRCode(savedTicket);
        return ticketRepository.save(savedTicket);


    }
}
