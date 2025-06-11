package com.ticketingportal.dto;

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
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private String organizerName;
    private String status;
    private String bannerImageUrl;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int maxCapacity;
    private String venueDetails;

    private List<TicketTypeDTO> ticketTypes;

    public EventDTO() {}

    public EventDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.location = event.getLocation();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        this.organizerName = event.getOrganizerName();
        this.status = event.getStatus();
        this.bannerImageUrl = event.getBannerImageUrl();
        this.category = event.getCategory();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();
        this.maxCapacity = event.getMaxCapacity();
        this.venueDetails = event.getVenueDetails();
        this.ticketTypes = event.getTicketTypes().stream()
                .map(TicketTypeDTO::new)
                .collect(Collectors.toList());
    }
}
