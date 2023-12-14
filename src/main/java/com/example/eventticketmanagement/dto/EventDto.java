package com.example.eventticketmanagement.dto;

import lombok.*;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    @NonNull
    private Long id;

    @NonNull
    private String eventName;

    @NonNull
    private Date startsAt;

    @NonNull
    private String venue;

    private String description;

    private byte[] image;
}
