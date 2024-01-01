package com.example.eventticketmanagement.controller;

import com.example.eventticketmanagement.controller.helper.ControllerHelper;
import com.example.eventticketmanagement.dto.EventDto;
import com.example.eventticketmanagement.entity.EventEntity;
import com.example.eventticketmanagement.exception.EventWithResourceNotFound;
import com.example.eventticketmanagement.factory.EventDtoFactory;
import com.example.eventticketmanagement.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/")
public class EventController {

    private final EventRepository eventRepository;
    private final EventDtoFactory eventDtoFactory;
    private final ControllerHelper controllerHelper;

    private static final String GET_EVENT_BY_ID = "{id}/";
    private static final String UPDATE_EVENT_BY_ID = "{id}/";
    private static final String DELETE_EVENT_BY_ID = "{id}/";
    private static final String UPLOAD_IMAGE_TO_EVENT = "{id}/uploadImage";

    @GetMapping()
    public List<EventDto> getAllEvents() {
        List<EventEntity> allEventEntities = eventRepository.findAll();

        return allEventEntities.stream()
                .map(eventDtoFactory::makeEventDto)
                .toList();
    }

    @GetMapping(GET_EVENT_BY_ID)
    public EventDto getEventById(@PathVariable Long id) {
        EventEntity foundEventEntity = controllerHelper.findEventEntityById(id);

        return eventDtoFactory.makeEventDto(foundEventEntity);
    }


    @PostMapping
    @PreAuthorize("@securityService.hasAdminRole()")
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
    @PreAuthorize("@securityService.hasAdminRole()")
    public ResponseEntity<EventDto> updateEventById(@PathVariable Long id, @RequestBody EventDto eventDto) {
        EventEntity foundEventEntity = controllerHelper.findEventEntityById(id);

        foundEventEntity.setEventName(eventDto.getEventName());
        foundEventEntity.setDescription(eventDto.getDescription());
        foundEventEntity.setVenue(eventDto.getVenue());
        foundEventEntity.setImage(eventDto.getImage());

        EventEntity updateEventEntity = eventRepository.save(foundEventEntity);
        EventDto updateEventDto = eventDtoFactory.makeEventDto(updateEventEntity);

        return ResponseEntity.status(HttpStatus.OK).body(updateEventDto);
    }

    @DeleteMapping(DELETE_EVENT_BY_ID)
    @PreAuthorize("@securityService.hasAdminRole()")
    public ResponseEntity<Void> deleteEventById(@PathVariable Long id) {
        Optional<EventEntity> eventEntity = eventRepository.findById(id);

        if (eventEntity.isEmpty()) {
            throw new EventWithResourceNotFound("event with id %d was not found".formatted(id));
        }

        eventRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(UPLOAD_IMAGE_TO_EVENT)
    @PreAuthorize("@securityService.hasAdminRole()")
    public ResponseEntity<String> uploadImageToEvent(@PathVariable Long id, @RequestBody MultipartFile file) {
        EventEntity eventEntity = controllerHelper.findEventEntityById(id);

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please upload a file");
            }

            byte[] imageBytes = file.getBytes();

            eventEntity.setImage(imageBytes);
            eventRepository.save(eventEntity);

            return ResponseEntity.status(HttpStatus.OK).body("your image was updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

}
