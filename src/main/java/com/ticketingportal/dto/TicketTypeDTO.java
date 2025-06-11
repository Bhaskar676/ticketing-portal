package com.ticketingportal.dto;

import com.ticketingportal.model.TicketType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketTypeDTO {

    private Long id;

    @NotBlank(message = "Ticket type name is required")
    private String name;

    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    private double price;

    @Min(value = 1, message = "Available seats must be at least 1")
    private int availableSeats;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private LocalDateTime saleStartDate;

    private LocalDateTime saleEndDate;

    @DecimalMin(value = "0.0", inclusive = true, message = "Discount price must be non-negative")
    private Double discountPrice;

    private boolean isRefundable;

    @Size(max = 100, message = "Seat section cannot exceed 100 characters")
    private String seatSection;

    public TicketTypeDTO() {}

    // Mapping constructor
    public TicketTypeDTO(TicketType ticketType) {
        this.id = ticketType.getId();
        this.name = ticketType.getName();
        this.price = ticketType.getPrice();
        this.availableSeats = ticketType.getAvailableSeats();
        this.description = ticketType.getDescription();
        this.saleStartDate = ticketType.getSaleStartDate();
        this.saleEndDate = ticketType.getSaleEndDate();
        this.discountPrice = ticketType.getDiscountPrice();
        this.isRefundable = ticketType.isRefundable();
        this.seatSection = ticketType.getSeatSection();
    }
}
