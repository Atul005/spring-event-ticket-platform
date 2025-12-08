package com.learn.spring.tickets.services;

import com.learn.spring.tickets.domain.CreateEventRequest;
import com.learn.spring.tickets.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest eventRequest);

    Page<Event> listEventForOrganizer(UUID organizerId, Pageable pageable);


}
