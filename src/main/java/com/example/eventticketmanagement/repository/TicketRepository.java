package com.example.eventticketmanagement.repository;

import com.example.eventticketmanagement.entity.TicketEntity;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    @Cacheable(value = "TicketRepository::getAllTicketsByUserId", key = "#userId")
    List<TicketEntity> getAllTicketsByUserId(Long userId, Sort sort);

    @Cacheable(value = "TicketRepository::tickets", key = "#id")
    @Override
    @NonNull
    Optional<TicketEntity> findById(@NonNull Long id);


    @CacheEvict(value = "TicketRepository::tickets")
    @Cacheable(value = "TicketRepository::tickets", key = "#entity.id")
    @Override
    <S extends TicketEntity> @NonNull S save(@NonNull S entity);

    @CacheEvict(value = "TicketRepository::tickets", key = "#id")
    void deleteById(@NonNull Long id);
}
