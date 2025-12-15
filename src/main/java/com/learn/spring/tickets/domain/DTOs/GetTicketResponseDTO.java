package com.learn.spring.tickets.domain.DTOs;

import com.learn.spring.tickets.domain.entities.TicketStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTicketResponseDTO {

    private UUID id;
    private TicketStatusEnum status;
    private Double price;
    private String description;
    private String eventName;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;




}
