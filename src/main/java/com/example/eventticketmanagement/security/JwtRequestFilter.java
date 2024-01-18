package com.example.eventticketmanagement.security;

import com.example.eventticketmanagement.exception.JwtKeyExpiredException;
import com.example.eventticketmanagement.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7);

            try {
                username = jwtUtils.getUsername(jwt);
            } catch (ExpiredJwtException e) {
                log.info(jwt);
                throw new JwtKeyExpiredException("jwt token is expired");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() !=  null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    new ArrayList<>(List.of(new SimpleGrantedAuthority(jwtUtils.getUsername(jwt))))
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
