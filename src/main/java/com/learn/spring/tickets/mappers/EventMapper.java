package com.learn.spring.tickets.mappers;


import com.learn.spring.tickets.domain.CreateEventRequest;
import com.learn.spring.tickets.domain.CreateTicketTypeRequest;
import com.learn.spring.tickets.domain.DTOs.*;
import com.learn.spring.tickets.domain.entities.Event;
import com.learn.spring.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDTO(CreateTicketTypeRequestDTO dto);

    @Mapping(source = "eventStatus", target = "eventStatus")
    CreateEventRequest fromDTO(CreateEventRequestDTO dto);

    CreateTicketTypeResponseDTO toDTO(TicketType ticketType);

    CreateEventResponseDTO toDTO(Event event);

    ListEventTicketTypeResponseDTO toListEventTicketTypeDTO(TicketType ticketType);

    ListEventResponseDTO toListEventResponseDTO(Event event);

}
