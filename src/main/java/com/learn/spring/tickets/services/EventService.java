package com.learn.spring.tickets.services;

import com.learn.spring.tickets.domain.CreateEventRequest;
import com.learn.spring.tickets.domain.UpdateEventRequest;
import com.learn.spring.tickets.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest eventRequest);

    Page<Event> listEventForOrganizer(UUID organizerId, Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId);

    Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest updateEventRequest);

    void deleteEventForOrganizer(UUID eventId, UUID userId);

    Page<Event> listPublishedEvents(Pageable pageable);
}
