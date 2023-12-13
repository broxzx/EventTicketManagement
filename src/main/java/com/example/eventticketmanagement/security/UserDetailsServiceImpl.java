package com.example.eventticketmanagement.security;

import com.example.eventticketmanagement.entity.UserEntity;
import com.example.eventticketmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity foundUserEntity = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("user with username %s was not found".formatted(username))
                );

        return UserDetailsImpl.build(foundUserEntity);
    }
}
