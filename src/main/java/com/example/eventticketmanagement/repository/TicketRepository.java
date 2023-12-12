package com.example.eventticketmanagement.repository;

import com.example.eventticketmanagement.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

}
