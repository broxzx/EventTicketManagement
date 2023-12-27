package com.example.eventticketmanagement.controller.helper;

import com.example.eventticketmanagement.entity.EventEntity;
import com.example.eventticketmanagement.exception.EventWithResourceNotFound;
import com.example.eventticketmanagement.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Transactional
public class ControllerHelper {

    private final EventRepository eventRepository;

    public EventEntity findEventEntityById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(
                        () -> new EventWithResourceNotFound("event with id %d was not found".formatted(id))
                );
    }
}

