package com.example.eventticketmanagement.dto;

import com.example.eventticketmanagement.entity.Role;
import lombok.Data;

@Data
public class UserRequestJwt {


    private String username;

    private String password;

    private String email;

    private byte[] profileImage;
}
