package com.ticketingportal.controller;

import com.ticketingportal.dto.EventDTO;
import com.ticketingportal.model.Event;
import com.ticketingportal.model.TicketType;
import com.ticketingportal.repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        return ResponseEntity.ok(new EventDTO(event));
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventDTO>> getEventsByCategory(@PathVariable String category) {
        List<Event> events = eventRepository.findByCategoryIgnoreCase(category);
        List<EventDTO> eventDTOs = events.stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }
    @GetMapping("/startDate")
    public ResponseEntity<List<EventDTO>> getEventsByStartDateBetween(
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end) {
        List<Event> events = eventRepository.findByEventStartDateBetween(start, end);
        List<EventDTO> eventDTOs = events.stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }
    @GetMapping("/search/name")
    public ResponseEntity<List<EventDTO>> searchEventsByName(@RequestParam String query) {
        List<Event> events = eventRepository.findByNameContainingIgnoreCase(query);
        List<EventDTO> eventDTOs = events.stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }
    @GetMapping("/search/category")
    public ResponseEntity<List<EventDTO>> searchEventsByCategory(@RequestParam String query) {
        List<Event> events = eventRepository.findByCategoryContainingIgnoreCase(query);
        List<EventDTO> eventDTOs = events.stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDTOs);
    }



    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setEventStartDate(eventDTO.getEventStartDate());
        event.setEventEndDate(eventDTO.getEventEndDate());
        event.setOrganizerName(eventDTO.getOrganizerName());
        event.setStatus(eventDTO.getStatus());
        event.setBannerImageUrl(eventDTO.getBannerImageUrl());
        event.setCategory(eventDTO.getCategory());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        event.setMaxCapacity(eventDTO.getMaxCapacity());
        event.setVenueDetails(eventDTO.getVenueDetails());

        List<TicketType> ticketTypes = eventDTO.getTicketTypes().stream().map(ttDto -> {
            TicketType tt = new TicketType();
            tt.setName(ttDto.getName());
            tt.setPrice(ttDto.getPrice());
            tt.setAvailableSeats(ttDto.getAvailableSeats());
            tt.setDescription(ttDto.getDescription());
            tt.setSaleStartDate(ttDto.getSaleStartDate());
            tt.setSaleEndDate(ttDto.getSaleEndDate());
            tt.setDiscountPrice(ttDto.getDiscountPrice());
            tt.setRefundable(ttDto.isRefundable());
            tt.setSeatSection(ttDto.getSeatSection());
            tt.setEvent(event);
            return tt;
        }).toList();

        event.setTicketTypes(ticketTypes);

        Event savedEvent = eventRepository.save(event);

        return ResponseEntity.ok(new EventDTO(savedEvent));
    }

}
