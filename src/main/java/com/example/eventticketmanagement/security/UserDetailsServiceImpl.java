package com.example.eventticketmanagement.security;

import com.example.eventticketmanagement.entity.UserEntity;
import com.example.eventticketmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity foundUserEntity = userRepository.findByUsername(username);
        if (foundUserEntity == null) {
            throw new UsernameNotFoundException("user with name %s was not found".formatted(username));
        }

        return UserDetailsImpl.build(userRepository.findByUsername(username));
    }
}
