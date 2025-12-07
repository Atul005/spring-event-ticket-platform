package com.learn.spring.tickets.controllers;


import com.learn.spring.tickets.domain.CreateEventRequest;
import com.learn.spring.tickets.domain.DTOs.CreateEventRequestDTO;
import com.learn.spring.tickets.domain.DTOs.CreateEventResponseDTO;
import com.learn.spring.tickets.domain.entities.Event;
import com.learn.spring.tickets.mappers.EventMapper;
import com.learn.spring.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(params = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDTO createEventRequestDTO) {
        CreateEventRequest createEventRequest = eventMapper.fromDTO(createEventRequestDTO);
        UUID userID = UUID.fromString(jwt.getSubject());
        Event createdEvent = eventService.createEvent(userID, createEventRequest);
        CreateEventResponseDTO createdEventResponseDTO = eventMapper.toDTO(createdEvent);
        return new ResponseEntity<>(createdEventResponseDTO, HttpStatus.CREATED);

    }


}
