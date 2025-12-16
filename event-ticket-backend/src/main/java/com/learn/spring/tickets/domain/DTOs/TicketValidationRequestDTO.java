package com.learn.spring.tickets.domain.DTOs;


import com.learn.spring.tickets.domain.entities.TicketValidationMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDTO {

    private UUID id;
    private TicketValidationMethodEnum method;


}
