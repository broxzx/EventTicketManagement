package com.example.eventticketmanagement.factory;


import com.example.eventticketmanagement.dto.UserDto;
import com.example.eventticketmanagement.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoFactory {

    public UserDto makeUserDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .build();
    }
}
