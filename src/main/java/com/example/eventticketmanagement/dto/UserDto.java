package com.example.eventticketmanagement.dto;

import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NonNull
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    private byte[] profileImage;
}
