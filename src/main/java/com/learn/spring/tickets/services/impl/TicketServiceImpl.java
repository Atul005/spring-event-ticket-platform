package com.learn.spring.tickets.services.impl;

import com.learn.spring.tickets.domain.entities.Ticket;
import com.learn.spring.tickets.repositories.TicketRepository;
import com.learn.spring.tickets.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;


    @Override
    public Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(userId, pageable);
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID ticketId, UUID userId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId, userId);
    }
}
