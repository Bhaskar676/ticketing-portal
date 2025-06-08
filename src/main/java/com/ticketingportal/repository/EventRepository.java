package com.ticketingportal.repository;

import com.ticketingportal.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Override
    @EntityGraph(attributePaths = "ticketTypes")
    List<Event> findAll();
}
