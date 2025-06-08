package com.ticketingportal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import com.ticketingportal.model.TicketType;

@Data
public class TicketTypeDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private double price;
    @NotNull
    private int availableSeats;

    public TicketTypeDTO() {}

    // Mapping constructor
    public TicketTypeDTO(TicketType ticketType) {
        this.id = ticketType.getId();
        this.name = ticketType.getName();
        this.price = ticketType.getPrice();
        this.availableSeats = ticketType.getAvailableSeats();
    }
}
