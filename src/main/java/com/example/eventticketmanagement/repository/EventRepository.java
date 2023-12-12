package com.example.eventticketmanagement.repository;

import com.example.eventticketmanagement.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<TicketEntity, Long> {

}
