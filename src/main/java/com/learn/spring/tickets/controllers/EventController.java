package com.learn.spring.tickets.controllers;


import com.learn.spring.tickets.domain.CreateEventRequest;
import com.learn.spring.tickets.domain.DTOs.CreateEventRequestDTO;
import com.learn.spring.tickets.domain.DTOs.CreateEventResponseDTO;
import com.learn.spring.tickets.domain.DTOs.ListEventResponseDTO;
import com.learn.spring.tickets.domain.entities.Event;
import com.learn.spring.tickets.mappers.EventMapper;
import com.learn.spring.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDTO createEventRequestDTO) {
        CreateEventRequest createEventRequest = eventMapper.fromDTO(createEventRequestDTO);
        UUID userID = getUserID(jwt);
        System.out.println(userID);
        Event createdEvent = eventService.createEvent(userID, createEventRequest);
        CreateEventResponseDTO createdEventResponseDTO = eventMapper.toDTO(createdEvent);
        return new ResponseEntity<>(createdEventResponseDTO, HttpStatus.CREATED);

    }



    @GetMapping
    public ResponseEntity<Page<ListEventResponseDTO>> listEvents(
            @AuthenticationPrincipal Jwt jwt, Pageable pageable
    ){
        Page<Event> events = eventService.listEventForOrganizer(getUserID(jwt), pageable);
        return ResponseEntity.ok(events.map(eventMapper::toListEventResponseDTO));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<CreateEventResponseDTO> getEventById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ){
        UUID userID = getUserID(jwt);
        return eventService.getEventForOrganizer(userID, eventId)
                .map(eventMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private static UUID getUserID(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }

}
