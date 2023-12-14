package com.example.eventticketmanagement.factory;

import com.example.eventticketmanagement.dto.EventDto;
import com.example.eventticketmanagement.entity.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventDtoFactory {

    public EventDto makeEventDto(EventEntity event) {
        return EventDto.builder()
                .id(event.getId())
                .eventName(event.getEventName())
                .startsAt(event.getStartsAt())
                .venue(event.getVenue())
                .description(event.getDescription())
                .image(event.getImage())
                .build();
    }
}
