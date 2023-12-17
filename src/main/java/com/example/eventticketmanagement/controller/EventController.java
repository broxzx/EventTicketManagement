package com.example.eventticketmanagement.controller;

import com.example.eventticketmanagement.dto.EventDto;
import com.example.eventticketmanagement.entity.EventEntity;
import com.example.eventticketmanagement.exception.EventWithResourceNotFound;
import com.example.eventticketmanagement.factory.EventDtoFactory;
import com.example.eventticketmanagement.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/")
public class EventController {

    private final EventRepository eventRepository;
    private final EventDtoFactory eventDtoFactory;

    private static final String GET_EVENT_BY_ID = "{id}";
    private static final String UPDATE_EVENT_BY_ID = "{id}";
    private static final String DELETE_EVENT_BY_ID = "{id}";

    @GetMapping()
    public List<EventDto> getAllEvents() {
        List<EventEntity> allEventEntities = eventRepository.findAll();

        return allEventEntities.stream()
                .map(eventDtoFactory::makeEventDto)
                .toList();
    }

    @GetMapping(GET_EVENT_BY_ID)
    public EventDto getEventById(@PathVariable Long id) {
        Optional<EventEntity> foundTaskEntity = eventRepository.findById(id);

        if (foundTaskEntity.isEmpty()) {
            throw new EventWithResourceNotFound("event with id %d was not found".formatted(id));
        }

        return eventDtoFactory.makeEventDto(foundTaskEntity.get());
    }


    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {

        EventEntity eventEntity = EventEntity.builder()
                .eventName(eventDto.getEventName())
                .description(eventDto.getDescription())
                .venue(eventDto.getVenue())
                .startsAt(eventDto.getStartsAt())
                .image(eventDto.getImage())
                .build();

        EventEntity savedEventEntity = eventRepository.save(eventEntity);
        EventDto saveEventDto = eventDtoFactory.makeEventDto(savedEventEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveEventDto);
    }

    @PutMapping(UPDATE_EVENT_BY_ID)
    public ResponseEntity<EventDto> updateEventById(@PathVariable Long id, @RequestBody EventDto eventDto) {
        Optional<EventEntity> foundEventEntity = eventRepository.findById(id);

        if (foundEventEntity.isEmpty()) {
            throw new EventWithResourceNotFound("event with id %d was not found".formatted(id));
        }

        foundEventEntity.get().setEventName(eventDto.getEventName());
        foundEventEntity.get().setDescription(eventDto.getDescription());
        foundEventEntity.get().setVenue(eventDto.getVenue());
        foundEventEntity.get().setImage(eventDto.getImage());

        EventEntity updateEventEntity = eventRepository.save(foundEventEntity.get());
        EventDto updateEventDto = eventDtoFactory.makeEventDto(updateEventEntity);

        return ResponseEntity.status(HttpStatus.OK).body(updateEventDto);
    }

    @DeleteMapping(DELETE_EVENT_BY_ID)
    public ResponseEntity<Void> deleteEventById(@PathVariable Long id) {
        Optional<EventEntity> eventEntity = eventRepository.findById(id);

        if (eventEntity.isEmpty()) {
            throw new EventWithResourceNotFound("event with id %d was not found".formatted(id));
        }

        eventRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
