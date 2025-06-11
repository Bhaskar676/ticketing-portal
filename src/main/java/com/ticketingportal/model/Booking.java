package com.ticketingportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull
    private Event event;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    @NotNull
    private TicketType ticketType;

    @NotBlank
    @Size(max = 100)
    private String userName;

    @Min(1)
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = true)
    private double totalAmount;

    @NotNull
    private LocalDateTime bookingDate;

    @NotBlank
    @Pattern(regexp = "PENDING|CONFIRMED|CANCELLED")
    private String status; // e.g. PENDING, CONFIRMED, CANCELLED

    @NotBlank
    @Pattern(regexp = "PAID|UNPAID|REFUNDED")
    private String paymentStatus; // e.g. PAID, UNPAID, REFUNDED

    @NotBlank
    private String paymentMethod; // e.g. CREDIT_CARD, UPI, etc.

    private String transactionId; // reference to a payment gateway transaction

    private boolean isRefundable; // if the booking is eligible for refund

    @Size(max = 500)
    private String notes;

    // Default constructor
    public Booking() {
    }

    // Constructor with all important fields
    public Booking(User user,
                   Event event,
                   TicketType ticketType,
                   String userName,
                   int quantity,
                   double totalAmount,
                   LocalDateTime bookingDate,
                   String status,
                   String paymentStatus,
                   String paymentMethod,
                   String transactionId,
                   boolean isRefundable,
                   String notes) {
        this.user = user;
        this.event = event;
        this.ticketType = ticketType;
        this.userName = userName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.isRefundable = isRefundable;
        this.notes = notes;
    }
}
