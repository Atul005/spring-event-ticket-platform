package com.learn.spring.tickets.repositories;

import com.learn.spring.tickets.domain.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    int countByTicketTypeId(UUID ticketTypeId);

    Page<Ticket> findByPurchaserId(UUID purchaserID, Pageable pageable);

    Optional<Ticket> findByIdAndPurchaserId(UUID ticketId, UUID purchaserId);


}
