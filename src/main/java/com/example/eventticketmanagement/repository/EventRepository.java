package com.example.eventticketmanagement.repository;

import com.example.eventticketmanagement.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

}
