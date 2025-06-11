package com.ticketingportal.dto;

import com.ticketingportal.model.Booking;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private Long id;
    private Long eventId;
    private String eventName;
    private Long userId;
    private String userName;
    private Long ticketTypeId;
    private String ticketTypeName;
    private int quantity;
    private double totalAmount;
    private LocalDateTime bookingDate;
    private String status;
    private String paymentStatus;
    private String paymentMethod;
    private String transactionId;
    private boolean isRefundable;
    private String notes;

    public BookingResponseDTO(Booking booking) {
        this.id = booking.getId();
        this.eventId = booking.getEvent().getId();
        this.eventName = booking.getEvent().getName();
        this.userId = booking.getUser().getId();
        this.userName = booking.getUser().getUsername();
        this.ticketTypeId = booking.getTicketType().getId();
        this.ticketTypeName = booking.getTicketType().getName();
        this.quantity = booking.getQuantity();
        this.totalAmount = booking.getTotalAmount();
        this.bookingDate = booking.getBookingDate();
        this.status = booking.getStatus();
        this.paymentStatus = booking.getPaymentStatus();
        this.paymentMethod = booking.getPaymentMethod();
        this.transactionId = booking.getTransactionId();
        this.isRefundable = booking.isRefundable();
        this.notes = booking.getNotes();
    }
}
