package com.learn.spring.tickets.mappers;

import com.learn.spring.tickets.domain.DTOs.GetTicketResponseDTO;
import com.learn.spring.tickets.domain.DTOs.ListTicketResponseDTO;
import com.learn.spring.tickets.domain.DTOs.ListTicketTicketTypeResponseDTO;
import com.learn.spring.tickets.domain.entities.Ticket;
import com.learn.spring.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketTicketTypeResponseDTO toListTicketTicketTypeResponseDTO(TicketType ticketType);

    ListTicketResponseDTO toListTicketResponseDTO(Ticket ticket);


    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "start", source = "ticket.ticketType.event.start")
    @Mapping(target = "end", source = "ticket.ticketType.event.end")
    @Mapping(target = "venue", source = "ticket.ticketType.event.venue")
    GetTicketResponseDTO toGetTicketResponseDTO(Ticket ticket);


}
