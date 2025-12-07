package com.learn.spring.tickets.domain.DTOs;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeRequestDTO {

    @NotBlank(message = "Ticket type is required")
    private String name;

    @NotBlank(message = "price is required")
    @PositiveOrZero(message = "Price must be zero or greater")
    private Double price;

    private String description;

    private Integer totalAvailable;
}
