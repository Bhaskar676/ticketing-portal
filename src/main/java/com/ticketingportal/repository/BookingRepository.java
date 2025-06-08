package com.ticketingportal.repository;

import com.ticketingportal.model.Booking;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
}
