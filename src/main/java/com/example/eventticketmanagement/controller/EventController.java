package com.example.eventticketmanagement.controller;

import com.example.eventticketmanagement.dto.EventDto;
import com.example.eventticketmanagement.entity.EventEntity;
import com.example.eventticketmanagement.exception.EventWithResourceNotFound;
import com.example.eventticketmanagement.factory.EventDtoFactory;
import com.example.eventticketmanagement.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    private static final String GET_EVENT_BY_ID = "{/id}";

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


    //TODO: rest of the methods
}
