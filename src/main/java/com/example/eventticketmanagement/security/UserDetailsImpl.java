package com.example.eventticketmanagement.security;

import com.example.eventticketmanagement.entity.Role;
import com.example.eventticketmanagement.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private byte[] profileImage;
    private List<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UserEntity user) {
        List<? extends GrantedAuthority> authorities = new ArrayList<>(List.of(new SimpleGrantedAuthority(user.getRole().name())));

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getProfileImage(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
