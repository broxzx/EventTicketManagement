package com.example.eventticketmanagement.repository;

import com.example.eventticketmanagement.entity.Role;
import com.example.eventticketmanagement.entity.UserEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Cacheable(value = "UserRepository::findByRole", key = "#role")
    List<UserEntity> findByRole(Role role);

    Optional<UserEntity> findByUsername(String username);
}
