package com.ticketingportal.repository;

import com.ticketingportal.model.Booking;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
    List<Booking> findByUserId(Long userId);

    List<Booking> findByEventId(Long eventId);

    List<Booking> findByStatus(String status);

    List<Booking> findByPaymentStatus(String paymentStatus);

    Optional<Booking> findByTransactionId(String transactionId);

    List<Booking> findByBookingDateBetween(LocalDateTime start, LocalDateTime end);

    List<Booking> findByStatusContainingIgnoreCase(String query);

    List<Booking> findByStatusIgnoreCase(String status);

    List<Booking> findByPaymentStatusIgnoreCase(String paymentStatus);



}
