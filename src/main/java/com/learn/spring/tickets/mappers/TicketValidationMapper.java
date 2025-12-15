package com.learn.spring.tickets.mappers;

import com.learn.spring.tickets.domain.DTOs.TicketValidationRequestDTO;
import com.learn.spring.tickets.domain.DTOs.TicketValidationResponseDTO;
import com.learn.spring.tickets.domain.entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    TicketValidation fromDTO(TicketValidationRequestDTO ticketValidationRequestDTO);


    @Mapping(target = "ticketId", source = "ticket.id")
    TicketValidationResponseDTO toDTO(TicketValidation ticketValidation);


}
