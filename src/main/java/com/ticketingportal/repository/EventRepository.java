package com.ticketingportal.repository;

import com.ticketingportal.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Override
    @EntityGraph(attributePaths = "ticketTypes")
    List<Event> findAll();
    List<Event> findByCategoryIgnoreCase(String category);
    List<Event> findByEventStartDateBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByNameContainingIgnoreCase(String query);
    List<Event> findByCategoryContainingIgnoreCase(String query);

}
