package com.learn.spring.tickets.services;

import com.learn.spring.tickets.domain.CreateEventRequest;
import com.learn.spring.tickets.domain.entities.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest eventRequest);
}
