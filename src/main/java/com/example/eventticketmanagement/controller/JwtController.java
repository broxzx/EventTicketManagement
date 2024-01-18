package com.example.eventticketmanagement.controller;

import com.example.eventticketmanagement.dto.JwtRequest;
import com.example.eventticketmanagement.dto.UserRequestJwt;
import com.example.eventticketmanagement.entity.UserEntity;
import com.example.eventticketmanagement.security.UserDetailsServiceImpl;
import com.example.eventticketmanagement.service.UserService;
import com.example.eventticketmanagement.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class JwtController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authentication;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> authentication(@RequestBody JwtRequest jwtRequest) {
        try {
            authentication.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user cannot be authorized");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserRequestJwt userRequestJwt) {
        UserEntity user = userService.saveUser(
                new UserEntity(null,
                        userRequestJwt.getUsername(),
                        userRequestJwt.getPassword(),
                        userRequestJwt.getEmail(),
                        null,
                        userRequestJwt.getProfileImage(),
                        null)
        );

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
