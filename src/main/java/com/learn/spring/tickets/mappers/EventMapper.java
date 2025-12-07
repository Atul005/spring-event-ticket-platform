package com.learn.spring.tickets.mappers;


import com.learn.spring.tickets.domain.CreateEventRequest;
import com.learn.spring.tickets.domain.CreateTicketTypeRequest;
import com.learn.spring.tickets.domain.DTOs.CreateEventRequestDTO;
import com.learn.spring.tickets.domain.DTOs.CreateEventResponseDTO;
import com.learn.spring.tickets.domain.DTOs.CreateTicketTypeRequestDTO;
import com.learn.spring.tickets.domain.DTOs.CreateTicketTypeResponseDTO;
import com.learn.spring.tickets.domain.entities.Event;
import com.learn.spring.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDTO(CreateTicketTypeRequestDTO dto);

    CreateEventRequest fromDTO(CreateEventRequestDTO dto);

    CreateTicketTypeResponseDTO toDTO(TicketType ticketType);

    CreateEventResponseDTO toDTO(Event event);

}
