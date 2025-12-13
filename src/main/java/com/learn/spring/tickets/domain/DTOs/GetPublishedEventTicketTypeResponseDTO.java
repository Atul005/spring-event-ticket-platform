package com.learn.spring.tickets.domain.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPublishedEventTicketTypeResponseDTO {
    private UUID id;
    private String name;
    private Double price;
    private String description;
}
