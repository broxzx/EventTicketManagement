package com.example.eventticketmanagement.repository;

import com.example.eventticketmanagement.entity.TicketEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> getAllTicketsByUserId(Long userId, Sort sort);
}
