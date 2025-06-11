package com.ticketingportal.controller;

import com.ticketingportal.dto.BookingRequest;
import com.ticketingportal.dto.BookingResponseDTO;
import com.ticketingportal.model.Booking;
import com.ticketingportal.model.Event;
import com.ticketingportal.model.TicketType;
import com.ticketingportal.model.User;
import com.ticketingportal.repository.BookingRepository;
import com.ticketingportal.repository.EventRepository;
import com.ticketingportal.repository.TicketTypeRepository;
import com.ticketingportal.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;

    public BookingController(BookingRepository bookingRepository,
                             EventRepository eventRepository,
                             TicketTypeRepository ticketTypeRepository,
                             UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @GetMapping
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingResponseDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        return ResponseEntity.ok(new BookingResponseDTO(booking));
    }

    @GetMapping("/date")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByDateRange(
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end) {
        List<BookingResponseDTO> results = bookingRepository.findByBookingDateBetween(start, end)
                .stream()
                .map(BookingResponseDTO::new)
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByStatus(@PathVariable String status) {
        List<BookingResponseDTO> results = bookingRepository.findByStatusIgnoreCase(status)
                .stream()
                .map(BookingResponseDTO::new)
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByEventId(@PathVariable Long eventId) {
        List<BookingResponseDTO> results = bookingRepository.findByEventId(eventId)
                .stream()
                .map(BookingResponseDTO::new)
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/paymentStatus/{paymentStatus}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByPaymentStatus(@PathVariable String paymentStatus) {
        List<BookingResponseDTO> results = bookingRepository.findByPaymentStatusIgnoreCase(paymentStatus)
                .stream()
                .map(BookingResponseDTO::new)
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUserId(@PathVariable Long userId) {
        List<BookingResponseDTO> results = bookingRepository.findByUserId(userId)
                .stream()
                .map(BookingResponseDTO::new)
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search/status")
    public ResponseEntity<List<BookingResponseDTO>> searchBookingsByStatus(@RequestParam String query) {
        List<BookingResponseDTO> results = bookingRepository.findByStatusContainingIgnoreCase(query)
                .stream()
                .map(BookingResponseDTO::new)
                .toList();
        return ResponseEntity.ok(results);
    }



    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        Event event = eventRepository.findById(bookingRequest.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        TicketType ticketType = ticketTypeRepository.findById(bookingRequest.getTicketTypeId())
                .orElseThrow(() -> new RuntimeException("Ticket type not found"));

        if (!ticketType.getEvent().getId().equals(event.getId())) {
            throw new RuntimeException("Ticket type does not belong to the specified event.");
        }

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (ticketType.getAvailableSeats() < bookingRequest.getNumberOfTickets()) {
            throw new RuntimeException("Not enough available seats for this ticket type.");
        }

        ticketType.setAvailableSeats(ticketType.getAvailableSeats() - bookingRequest.getNumberOfTickets());
        ticketTypeRepository.save(ticketType);

        double totalPrice = ticketType.getPrice() * bookingRequest.getNumberOfTickets();

        Booking booking = new Booking(
                user,
                event,
                ticketType,
                user.getUsername(),
                bookingRequest.getNumberOfTickets(),
                totalPrice,
                LocalDateTime.now(),
                "PENDING", // default status
                "UNPAID", // default payment status
                bookingRequest.getPaymentMethod(),
                bookingRequest.getTransactionId(),
                bookingRequest.isRefundable(),
                bookingRequest.getNotes()
        );

        Booking savedBooking = bookingRepository.save(booking);
        return ResponseEntity.ok(new BookingResponseDTO(savedBooking));
    }
}
