package com.example.eventticketmanagement.repository;

import com.example.eventticketmanagement.entity.Role;
import com.example.eventticketmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByRole(Role role);

    UserEntity findByUsername(String username);
}
