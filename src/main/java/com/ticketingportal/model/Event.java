package com.ticketingportal.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String location;
    private LocalDateTime eventDate;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<TicketType> ticketTypes = new ArrayList<>();

    public Event() {
    }

    public Event(String name, String description, String location, LocalDateTime eventDate, List<TicketType> ticketTypes) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.eventDate = eventDate;
        this.ticketTypes= ticketTypes;
    }
}
