package com.ticketingportal.controller;

import com.ticketingportal.model.Event;
import com.ticketingportal.model.TicketType;
import com.ticketingportal.repository.EventRepository;
import org.springframework.web.bind.annotation.*;
import com.ticketingportal.dto.EventDTO;
import com.ticketingportal.dto.TicketTypeDTO;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<EventDTO> getAllEvents(){
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public Event createEvent(@RequestBody EventDTO eventDTO){
        Event event= new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setEventDate(eventDTO.getEventDate());

        List<TicketType> ticketTypes = eventDTO.getTicketTypes().stream().map(ttDto -> {
            TicketType tt = new TicketType();
            tt.setName(ttDto.getName());
            tt.setPrice(ttDto.getPrice());
            tt.setAvailableSeats(ttDto.getAvailableSeats());
            tt.setEvent(event);
            return tt;
        }).toList();

        event.setTicketTypes((ticketTypes));

        return  eventRepository.save(event);
    }
}
