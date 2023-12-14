package com.example.eventticketmanagement.factory;

import com.example.eventticketmanagement.dto.TicketDto;
import com.example.eventticketmanagement.entity.TicketEntity;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoFactory {

    public TicketDto makeTicketDto(TicketEntity ticketEntity) {
        return TicketDto.builder()
                .id(ticketEntity.getId())
                .type(ticketEntity.getType())
                .price(ticketEntity.getPrice())
                .build();
    }
}
