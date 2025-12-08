package com.learn.spring.tickets.services.impl;

import com.learn.spring.tickets.domain.CreateEventRequest;
import com.learn.spring.tickets.domain.CreateTicketTypeRequest;
import com.learn.spring.tickets.domain.entities.Event;
import com.learn.spring.tickets.domain.entities.TicketType;
import com.learn.spring.tickets.domain.entities.User;
import com.learn.spring.tickets.exceptions.UserNotFoundException;
import com.learn.spring.tickets.repositories.EventRepository;
import com.learn.spring.tickets.repositories.UserRepository;
import com.learn.spring.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest eventRequest) {

        User organizer = userRepository.findById(organizerId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with '%s' not found", organizerId)
        ));

        Event newEvent = new Event();
        newEvent.setName(eventRequest.getName());
        newEvent.setStart(eventRequest.getStart());
        newEvent.setEnd(eventRequest.getEnd());
        newEvent.setEventStatus(eventRequest.getEventStatus());
        newEvent.setVenue(eventRequest.getVenue());
        newEvent.setSalesStart(eventRequest.getSalesStart());
        newEvent.setSalesEnd(eventRequest.getSalesEnd());
        newEvent.setTicketTypes(getTicketTypes(eventRequest.getTicketTypes(), newEvent));
        newEvent.setOrganizer(organizer);

        return eventRepository.save(newEvent);

    }

    @Override
    public Page<Event> listEventForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    private List<TicketType> getTicketTypes(List<CreateTicketTypeRequest> ticketTypesRequest, Event newEvent) {
        List<TicketType> ticketTypeList = new ArrayList<>();
        for (CreateTicketTypeRequest request: ticketTypesRequest){
            TicketType ticketType = new TicketType();
            ticketType.setName(request.getName());
            ticketType.setPrice(request.getPrice());
            ticketType.setDescription(request.getDescription());
            ticketType.setTotalAvailable(request.getTotalAvailable());
            ticketTypeList.add(ticketType);
            ticketType.setEvent(newEvent);
        }
        return ticketTypeList;
    }
}
