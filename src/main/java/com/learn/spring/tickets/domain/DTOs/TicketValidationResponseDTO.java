package com.learn.spring.tickets.domain.DTOs;


import com.learn.spring.tickets.domain.entities.TicketValidationMethodEnum;
import com.learn.spring.tickets.domain.entities.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDTO {

    private UUID ticketId;
    private TicketValidationStatusEnum status;


}
