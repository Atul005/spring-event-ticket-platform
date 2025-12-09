package com.learn.spring.tickets.domain.DTOs;

import com.learn.spring.tickets.domain.entities.EventStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListEventResponseDTO {

    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum eventStatus;
    private List<ListEventTicketTypeResponseDTO> ticketTypes = new ArrayList<>();



}
