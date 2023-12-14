package com.example.eventticketmanagement.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    @NonNull
    private Long id;

    @NonNull
    private String type;

    @NonNull
    private Long price;
}
