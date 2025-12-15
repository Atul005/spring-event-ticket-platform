package com.learn.spring.tickets.domain.DTOs;


import com.learn.spring.tickets.domain.entities.TicketStatusEnum;
import com.learn.spring.tickets.domain.entities.TicketType;
import com.learn.spring.tickets.domain.entities.TicketValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDTO {

    private UUID id;
    private TicketStatusEnum status;
    private ListTicketTicketTypeResponseDTO ticketType;

}
