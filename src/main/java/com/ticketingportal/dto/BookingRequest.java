package com.ticketingportal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotNull(message = "Ticket Type ID is required")
    private Long ticketTypeId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @Min(value = 1, message = "Must book at least 1 ticket")
    private int numberOfTickets;
}