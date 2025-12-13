package com.learn.spring.tickets.controllers;


import com.learn.spring.tickets.domain.DTOs.GetPublishedEventResponseDTO;
import com.learn.spring.tickets.domain.DTOs.ListPublishedEventResponseDTO;
import com.learn.spring.tickets.domain.entities.Event;
import com.learn.spring.tickets.mappers.EventMapper;
import com.learn.spring.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDTO>> listPublishedEvents(@RequestParam(required = false) String q, Pageable pageable){

        Page<Event> events;
        if( null != q && !q.trim().isEmpty()){
            events = eventService.searchPublishedEvents(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(events.map(eventMapper::toListPublishedEventResponseDTO));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventResponseDTO> getPublishedEventResponseDTOResponseEntity(@PathVariable UUID eventId){
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }



}
