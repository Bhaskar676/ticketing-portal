package com.ticketingportal.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//Fields: id, userId (or user entity later), eventId (many-to-one with Event), bookingDate, numberOfTickets,
// totalPrice.

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    private String userName;
    private int numberOfTickets;
    private double totalPrice;
    private LocalDateTime bookingDate;


    public Booking(){
    }

    public Booking(Event event, User user, int numberOfTickets, double totalPrice, LocalDateTime bookingDate) {
        this.event = event;
        this.user = user;
        this.userName = userName;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
    }
}
