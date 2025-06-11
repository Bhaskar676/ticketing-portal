package com.ticketingportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event name is required")
    @Size(max = 200, message = "Event name cannot exceed 200 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Location is required")
    @Size(max = 500, message = "Location cannot exceed 500 characters")
    private String location;

    @NotNull(message = "Event start date is required")
    private LocalDateTime eventStartDate;

    @NotNull(message = "Event end date is required")
    private LocalDateTime eventEndDate;

    @NotBlank(message = "Organizer name is required")
    @Size(max = 200, message = "Organizer name cannot exceed 200 characters")
    private String organizerName;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "ACTIVE|CANCELLED|POSTPONED", message = "Status must be ACTIVE, CANCELLED, or POSTPONED")
    private String status;

    @Size(max = 1000, message = "Banner image URL cannot exceed 1000 characters")
    private String bannerImageUrl;

    @NotBlank(message = "Category is required")
    @Size(max = 100, message = "Category cannot exceed 100 characters")
    private String category;

    private LocalDateTime createdAt; // timestamps for auditing

    private LocalDateTime updatedAt;

    @Min(value = 1, message = "Max capacity must be at least 1")
    private int maxCapacity;

    @Size(max = 1000, message = "Venue details cannot exceed 1000 characters")
    private String venueDetails;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<TicketType> ticketTypes = new ArrayList<>();

    public Event() {
    }

    public Event(String name,
                 String description,
                 String location,
                 LocalDateTime eventStartDate,
                 LocalDateTime eventEndDate,
                 String organizerName,
                 String status,
                 String bannerImageUrl,
                 String category,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt,
                 int maxCapacity,
                 String venueDetails,
                 List<TicketType> ticketTypes) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.organizerName = organizerName;
        this.status = status;
        this.bannerImageUrl = bannerImageUrl;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.maxCapacity = maxCapacity;
        this.venueDetails = venueDetails;
        this.ticketTypes = ticketTypes;
    }
}
