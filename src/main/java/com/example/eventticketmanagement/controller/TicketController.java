package com.example.eventticketmanagement.controller;

import com.example.eventticketmanagement.controller.helper.ControllerHelper;
import com.example.eventticketmanagement.dto.TicketDto;
import com.example.eventticketmanagement.entity.EventEntity;
import com.example.eventticketmanagement.entity.TicketEntity;
import com.example.eventticketmanagement.entity.UserEntity;
import com.example.eventticketmanagement.exception.TicketWithResourcesNotFound;
import com.example.eventticketmanagement.exception.UserWithResourceNotFound;
import com.example.eventticketmanagement.factory.TicketDtoFactory;
import com.example.eventticketmanagement.repository.EventRepository;
import com.example.eventticketmanagement.repository.TicketRepository;
import com.example.eventticketmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api/")
@RestController
@RequiredArgsConstructor
@Transactional
public class TicketController {

    private final TicketRepository ticketRepository;
    private final TicketDtoFactory ticketDtoFactory;
    private final EventRepository eventRepository;
    private final ControllerHelper controllerHelper;
    private final UserRepository userRepository;

    private static final String CREATE_TICKET = "{id}/ticket/";
    private static final String GET_ALL_TICKETS_BY_EVENT_ID = "{id}/ticket/";
    private static final String GET_TICKET_BY_ID = "{eventId}/ticket/{ticketId}/";
    private static final String UPDATE_TICKET_BY_ID = "{eventId}/ticket/{ticketId}/";
    private static final String DELETE_TICKET_BY_ID = "{eventId}/ticket/{ticketId}/";
    private static final String BUY_TICKET = "{eventId}/buyTicket/{ticketId}/";

    @GetMapping(GET_TICKET_BY_ID)
    public TicketDto getAllTicketsById(@PathVariable Long eventId, @PathVariable Long ticketId) {
        EventEntity foundEventEntity = controllerHelper.findEventEntityById(eventId);

        List<TicketEntity> ticketEntities = foundEventEntity.getListOfTickets();

        for (TicketEntity ticketEntity : ticketEntities) {
            if (Objects.equals(ticketEntity.getId(), ticketId)) {
                return ticketDtoFactory.makeTicketDto(ticketEntity);
            }
        }

        throw new TicketWithResourcesNotFound("ticket with id %d was not found".formatted(ticketId));
    }

    @PostMapping(CREATE_TICKET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketDto> createTicket(@PathVariable Long id, @RequestBody TicketDto ticketDto) {
        EventEntity foundEventEntity = controllerHelper.findEventEntityById(id);

        TicketEntity ticketEntity = TicketEntity.builder()
                .type(ticketDto.getType())
                .event(foundEventEntity)
                .price(ticketDto.getPrice())
                .build();

        foundEventEntity.addTicketToEvent(ticketEntity);

        ticketRepository.save(ticketEntity);

        return ResponseEntity.status(HttpStatus.OK).body(ticketDto);
    }

    @GetMapping(GET_ALL_TICKETS_BY_EVENT_ID)
    public List<TicketDto> getAllTicketsByEventId(@PathVariable Long id) {
        EventEntity foundEventEntity = controllerHelper.findEventEntityById(id);

        List<TicketEntity> foundTicketEntities = foundEventEntity.getListOfTickets();

        List<TicketDto> ticketDtos = new ArrayList<>();

        for (TicketEntity ticketEntity : foundTicketEntities) {
            TicketDto ticketDto = ticketDtoFactory.makeTicketDto(ticketEntity);
            ticketDtos.add(ticketDto);
        }

        return ticketDtos;
    }

    @PutMapping(UPDATE_TICKET_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketDto> updateTicketById(@PathVariable Long eventId, @PathVariable Long ticketId, @RequestBody TicketDto ticketDto) {
        EventEntity foundEventEntity = controllerHelper.findEventEntityById(eventId);

        List<TicketEntity> ticketEntities = foundEventEntity.getListOfTickets();

        TicketEntity foundTicketEntity = ticketEntities
                .stream()
                .filter(ticket -> Objects.equals(ticket.getId(), ticketId))
                .findFirst()
                .orElseThrow(
                        () ->  new TicketWithResourcesNotFound("ticket with id %d was not found".formatted(ticketId))
                );


       ticketEntities.remove(foundTicketEntity);

        TicketEntity ticketEntity = TicketEntity
                .builder()
                .id(ticketId)
                .type(ticketDto.getType())
                .price(ticketDto.getPrice())
                .event(foundEventEntity)
                .build();

        ticketEntities.add(ticketEntity);

        foundEventEntity.setListOfTickets(ticketEntities);

        ticketRepository.save(ticketEntity);
        eventRepository.save(foundEventEntity);

        return ResponseEntity.status(HttpStatus.OK).body(ticketDto);
    }

    @DeleteMapping(DELETE_TICKET_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTicketById(@PathVariable Long eventId, @PathVariable Long ticketId) {
        EventEntity eventEntity = controllerHelper.findEventEntityById(eventId);

        List<TicketEntity> ticketEntityList = eventEntity.getListOfTickets();

        TicketEntity foundTicketEntity = ticketEntityList
                .stream()
                .filter(ticket -> Objects.equals(ticket.getId(), eventId))
                .findFirst()
                .orElseThrow(
                        () ->  new TicketWithResourcesNotFound("ticket with id %d was not found".formatted(ticketId))
                );

        ticketEntityList.remove(foundTicketEntity);
        ticketRepository.deleteById(ticketId);
        eventRepository.save(eventEntity);

        return ResponseEntity.noContent().build();
    }


    @PutMapping(BUY_TICKET)
    public ResponseEntity<String> buyTicketById(@PathVariable Long eventId, @PathVariable Long ticketId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity foundUser = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UserWithResourceNotFound("user with name %s was not found".formatted(username))
                );


        EventEntity eventEntity = controllerHelper.findEventEntityById(eventId);
        TicketEntity ticketEntity = ticketRepository.findById(ticketId)
                .orElseThrow(
                        () -> new TicketWithResourcesNotFound("ticket with id %d was not found".formatted(ticketId))
                );

        List<TicketEntity> ticketEntities = foundUser.getListOfTickets();

        ticketEntities.add(ticketEntity);

        foundUser.setListOfTickets(ticketEntities);

        return ResponseEntity.status(HttpStatus.OK).body("ticket with id %d was successfully bought".formatted(ticketId));
    }
}
