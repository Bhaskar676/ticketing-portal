package com.ticketingportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ticket_types")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ticket type name is required")
    @Size(max = 100, message = "Ticket type name cannot exceed 100 characters")
    private String name;

    @Min(value = 1, message = "Available seats must be at least 1")
    private int availableSeats;

    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    private double price;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull(message = "Event is required")
    private Event event;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private LocalDateTime saleStartDate;

    private LocalDateTime saleEndDate;

    @DecimalMin(value = "0.0", inclusive = true, message = "Discount price must be non-negative")
    private Double discountPrice;

    private boolean isRefundable;

    @Size(max = 100, message = "Seat section cannot exceed 100 characters")
    private String seatSection;

    // Default constructor
    public TicketType() {}

    public TicketType(String name,
                      int availableSeats,
                      double price,
                      Event event,
                      String description,
                      LocalDateTime saleStartDate,
                      LocalDateTime saleEndDate,
                      Double discountPrice,
                      boolean isRefundable,
                      String seatSection) {
        this.name = name;
        this.availableSeats = availableSeats;
        this.price = price;
        this.event = event;
        this.description = description;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
        this.discountPrice = discountPrice;
        this.isRefundable = isRefundable;
        this.seatSection = seatSection;
    }
}
