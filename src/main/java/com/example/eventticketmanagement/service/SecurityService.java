package com.example.eventticketmanagement.service;

import com.example.eventticketmanagement.entity.Role;
import com.example.eventticketmanagement.entity.UserEntity;
import com.example.eventticketmanagement.exception.LoggingException;
import com.example.eventticketmanagement.exception.UserWithResourceNotFound;
import com.example.eventticketmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;

    public boolean canAccessUser(Authentication authentication, Long userId) {
        String username = authentication.getName();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserWithResourceNotFound("user with id %d was not found".formatted(userId))
                );

        return user.getUsername().equals(username) || user.getRole() == Role.ADMIN;
    }

    public boolean hasAdminRole() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (!authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            throw new LoggingException("you do not have access");
        }

        return true;
    }
}
