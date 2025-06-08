package com.ticketingportal.dto;

import com.ticketingportal.controller.EventController;
import com.ticketingportal.model.TicketType;
import com.ticketingportal.model.Event;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime eventDate;
    private List<TicketTypeDTO> ticketTypes;

    public EventDTO() {}

    public EventDTO(Event event){
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.location = event.getLocation();
        this.eventDate = event.getEventDate();
        this.ticketTypes = event.getTicketTypes().stream()
                .map(TicketTypeDTO::new)
                .collect(Collectors.toList());
    }
}
