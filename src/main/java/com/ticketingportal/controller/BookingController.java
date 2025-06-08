package com.ticketingportal.controller;

import com.ticketingportal.model.TicketType;
import com.ticketingportal.repository.BookingRepository;
import com.ticketingportal.model.Booking;
import com.ticketingportal.dto.BookingRequest;
import com.ticketingportal.repository.EventRepository;
import com.ticketingportal.model.Event;
import com.ticketingportal.repository.TicketTypeRepository;
import com.ticketingportal.repository.UserRepository;
import com.ticketingportal.model.User;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;

    public BookingController(BookingRepository bookingRepository, EventRepository eventRepository, TicketTypeRepository ticketTypeRepository, UserRepository userRepository){
        this.bookingRepository=bookingRepository;
        this.eventRepository=eventRepository;
        this.userRepository=userRepository;
        this.ticketTypeRepository = ticketTypeRepository;

    }

    @GetMapping
    public List<Booking>getAllBookings(){
        return bookingRepository.findAll();
    }

    @PostMapping
    public Booking createBooking(@Valid @RequestBody BookingRequest bookingRequest){
        Event event = eventRepository.findById(bookingRequest.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        TicketType ticketType = ticketTypeRepository.findById(bookingRequest.getTicketTypeId())
                .orElseThrow(() -> new RuntimeException("Ticket type not found"));

        if (!ticketType.getEvent().getId().equals(event.getId())){
            throw new RuntimeException("Ticket type does not belong to the specified event.");
        }


        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (ticketType.getAvailableSeats()<bookingRequest.getNumberOfTickets()){
            throw new RuntimeException("Not enough avaliable seats for this ticket type.");
        }

        ticketType.setAvailableSeats(ticketType.getAvailableSeats() - bookingRequest.getNumberOfTickets());
        ticketTypeRepository.save(ticketType);

        double totalPrice = ticketType.getPrice() * bookingRequest.getNumberOfTickets();
        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setTicketType(ticketType);
        booking.setUserName(user.getUsername());
        booking.setNumberOfTickets(bookingRequest.getNumberOfTickets());
        booking.setTotalPrice(totalPrice);
        booking.setBookingDate(LocalDateTime.now());

        return bookingRepository.save(booking);

    }
}
