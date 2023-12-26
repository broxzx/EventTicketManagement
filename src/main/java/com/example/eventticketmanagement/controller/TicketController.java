package com.example.eventticketmanagement.controller;

import com.example.eventticketmanagement.dto.TicketDto;
import com.example.eventticketmanagement.entity.EventEntity;
import com.example.eventticketmanagement.entity.TicketEntity;
import com.example.eventticketmanagement.exception.EventWithResourceNotFound;
import com.example.eventticketmanagement.exception.TicketWithResourcesNotFound;
import com.example.eventticketmanagement.factory.TicketDtoFactory;
import com.example.eventticketmanagement.repository.EventRepository;
import com.example.eventticketmanagement.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/")
@RestController
@RequiredArgsConstructor
@Transactional
public class TicketController {

    private final TicketRepository ticketRepository;
    private final TicketDtoFactory ticketDtoFactory;
    private final EventRepository eventRepository;

    private static final String CREATE_TICKET = "{id}/ticket";
    private static final String GET_TICKET_BY_ID = "{id}/ticket";

    @GetMapping(GET_TICKET_BY_ID)
    public TicketDto getAllTicketsById(@PathVariable Long id) {
        Optional<TicketEntity> ticketEntity = ticketRepository.findById(id);

        if (ticketEntity.isEmpty()) {
            throw new TicketWithResourcesNotFound("ticket with id %d was not found".formatted(id));
        }

        return ticketDtoFactory.makeTicketDto(ticketEntity.get());
    }

    @PostMapping(CREATE_TICKET)
    public ResponseEntity<TicketDto> createTicket(@PathVariable Long id, @RequestBody TicketDto ticketDto) {
        Optional<EventEntity> foundEventEntity = eventRepository.findById(id);

        if (foundEventEntity.isEmpty()) {
            throw new EventWithResourceNotFound("event with id %d was not found".formatted(id));
        }

        TicketEntity ticketEntity = TicketEntity.builder()
                .type(ticketDto.getType())
                .event(foundEventEntity.get())
                .price(ticketDto.getPrice())
                .build();

        foundEventEntity.get().addTicketToEvent(ticketEntity);

        ticketRepository.save(ticketEntity);

        return ResponseEntity.status(HttpStatus.OK).body(ticketDto);
    }
}
