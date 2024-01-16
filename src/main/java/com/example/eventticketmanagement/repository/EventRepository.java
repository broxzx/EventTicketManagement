package com.example.eventticketmanagement.repository;

import com.example.eventticketmanagement.entity.EventEntity;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Cacheable(value="EventRepository::events", key = "#id")
    Optional<EventEntity> findEventEntityById(Long id);

    @CacheEvict(value = "EventRepository::findAll")
    @Cacheable(value = "EventRepository::events", key = "#entity")
    @Override
    <S extends EventEntity> @NonNull S save(@NonNull S entity);

    @Cacheable(value = "EventRepository::findAll", key = "#example")
    @Override
    <S extends EventEntity> @NonNull List<S> findAll(@NonNull Example<S> example);

    @CacheEvict(value = "EventRepository::events", key = "#id")
    void deleteById(@NonNull Long id);


}
