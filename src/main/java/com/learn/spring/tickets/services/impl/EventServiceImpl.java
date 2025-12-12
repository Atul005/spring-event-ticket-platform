package com.learn.spring.tickets.services.impl;

import com.learn.spring.tickets.domain.CreateEventRequest;
import com.learn.spring.tickets.domain.CreateTicketTypeRequest;
import com.learn.spring.tickets.domain.UpdateEventRequest;
import com.learn.spring.tickets.domain.UpdateTicketTypeRequest;
import com.learn.spring.tickets.domain.entities.Event;
import com.learn.spring.tickets.domain.entities.EventStatusEnum;
import com.learn.spring.tickets.domain.entities.TicketType;
import com.learn.spring.tickets.domain.entities.User;
import com.learn.spring.tickets.exceptions.EventNotFoundException;
import com.learn.spring.tickets.exceptions.EventUpdateException;
import com.learn.spring.tickets.exceptions.TicketTypeNotFoundException;
import com.learn.spring.tickets.exceptions.UserNotFoundException;
import com.learn.spring.tickets.repositories.EventRepository;
import com.learn.spring.tickets.repositories.UserRepository;
import com.learn.spring.tickets.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
        return eventRepository.findByIdAndOrganizerId(eventId, organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest updateEventRequest) {
        if(null == updateEventRequest.getId()){
            throw new EventUpdateException("Event Id cannot be null");
        }

        if(eventId == updateEventRequest.getId()){
            throw new EventUpdateException("Event update failed as provided id -> "+eventId +" and requested id do not match -> "+updateEventRequest.getId());
        }

        Event existingEvent = eventRepository.findByIdAndOrganizerId(eventId, organizerId)
                                             .orElseThrow(() -> new EventNotFoundException(String.format("Event with id '%s' not found", eventId)));


        return updateEvent(existingEvent, updateEventRequest);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID eventId, UUID userId) {
        getEventForOrganizer(userId, eventId).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByEventStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    private Event updateEvent(Event existingEvent, UpdateEventRequest updateEventRequest) {
        existingEvent.setName(updateEventRequest.getName());
        existingEvent.setStart(updateEventRequest.getStart());
        existingEvent.setEnd(updateEventRequest.getEnd());
        existingEvent.setVenue(updateEventRequest.getVenue());
        existingEvent.setSalesStart(updateEventRequest.getSalesStart());
        existingEvent.setSalesEnd(updateEventRequest.getSalesEnd());
        existingEvent.setEventStatus(updateEventRequest.getEventStatus());
        updateTicketType(existingEvent, updateEventRequest);

        Set<UUID> requestTicketTypes = updateEventRequest.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes()
                     .removeIf(ticketType ->
                             !requestTicketTypes.contains(ticketType.getId()));

        Map<UUID, TicketType> ticketTypeMap = existingEvent.getTicketTypes()
                                                           .stream()
                                                           .collect(Collectors.toMap(TicketType::getId, Function.identity()));
        for(UpdateTicketTypeRequest ticketTypeRequest : updateEventRequest.getTicketTypes()){
            if(null == ticketTypeRequest.getId()){
                TicketType ticketType = new TicketType();
                ticketType.setName(ticketTypeRequest.getName());
                ticketType.setPrice(ticketTypeRequest.getPrice());
                ticketType.setDescription(ticketTypeRequest.getDescription());
                ticketType.setTotalAvailable(ticketTypeRequest.getTotalAvailable());
                ticketType.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketType);
            } else if (ticketTypeMap.containsKey(ticketTypeRequest.getId())) {
                TicketType existingTicket = ticketTypeMap.get(ticketTypeRequest.getId());
                existingTicket.setDescription(ticketTypeRequest.getDescription());
                existingTicket.setPrice(ticketTypeRequest.getPrice());
                existingTicket.setName(ticketTypeRequest.getName());
                existingTicket.setTotalAvailable(ticketTypeRequest.getTotalAvailable());
            }
            else {
                throw new TicketTypeNotFoundException(String.format(
                        "Ticket type with id '%s' does not exist.", ticketTypeRequest.getId()
                ));
            }
        }
        return eventRepository.save(existingEvent);
    }


    private void updateTicketType(Event existingEvent, UpdateEventRequest updateEventRequest) {
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
