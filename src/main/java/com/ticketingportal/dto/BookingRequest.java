package com.ticketingportal.dto;

import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Payment method is required")
    private String paymentMethod; // e.g. CREDIT_CARD, UPI, etc.

    private String transactionId; // optional, might be set by payment gateway

    private boolean isRefundable; // optional

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
}
