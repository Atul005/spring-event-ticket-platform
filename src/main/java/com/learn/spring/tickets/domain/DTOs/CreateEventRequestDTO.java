package com.learn.spring.tickets.domain.DTOs;
import com.learn.spring.tickets.domain.entities.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDTO {

    @NotBlank(message = "Event name is required")
    private String name;

    private LocalDateTime start;
    private LocalDateTime end;

    @NotBlank(message = "Venue information is required")
    private String venue;


    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;

    @NotBlank(message = "Event status must be provided")
    private EventStatusEnum eventStatus;

    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    private List<CreateTicketTypeRequestDTO> ticketTypes;


}
