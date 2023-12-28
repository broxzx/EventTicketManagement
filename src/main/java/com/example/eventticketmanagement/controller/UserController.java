package com.example.eventticketmanagement.controller;

import com.example.eventticketmanagement.dto.UserDto;
import com.example.eventticketmanagement.entity.UserEntity;
import com.example.eventticketmanagement.exception.UserWithResourceNotFound;
import com.example.eventticketmanagement.factory.UserDtoFactory;
import com.example.eventticketmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Transactional
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserDtoFactory userDtoFactory;

    private static final String GET_USER_BY_ID = "/{id}/";
    private static final String UPDATE_USER_BY_ID = "/{id}/";
    private static final String UPLOAD_USER_IMAGE_BY_ID = "/{id}/uploadImage";


    @GetMapping(GET_USER_BY_ID)
    @PostAuthorize("returnObject.body.username == principal.username")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserEntity foundUserEntity = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserWithResourceNotFound("user with id %d was not found".formatted(id))
                );


        return ResponseEntity.status(HttpStatus.OK).body(userDtoFactory.makeUserDto(foundUserEntity));
    }

    @PutMapping(UPDATE_USER_BY_ID)
    @PostAuthorize("returnObject.body.username == principal.username")
    public ResponseEntity<UserDto> updateUserEntityById(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserEntity foundUserEntity = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserWithResourceNotFound("user with id %d was not found".formatted(id))
                );

        foundUserEntity.setUsername(userDto.getUsername());
        foundUserEntity.setEmail(userDto.getEmail());
        foundUserEntity.setProfileImage(userDto.getProfileImage());
        userRepository.save(foundUserEntity);

        UserDto userDtoResponse = UserDto.builder()
                .id(foundUserEntity.getId())
                .username(foundUserEntity.getUsername())
                .email(foundUserEntity.getEmail())
                .profileImage(foundUserEntity.getProfileImage())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(userDtoResponse);
    }

    @PostMapping(UPLOAD_USER_IMAGE_BY_ID)
    @PostAuthorize("returnObject.body.username == principal.username")
    public ResponseEntity<String> uploadImageByUserId(@PathVariable Long id, @RequestBody MultipartFile file) {
        UserEntity foundUserEntity = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserWithResourceNotFound("user with id %d was not found".formatted(id))
                );

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please upload a file");
           }

            byte[] imageBytes = file.getBytes();

            foundUserEntity.setProfileImage(imageBytes);

            userRepository.save(foundUserEntity);

            return ResponseEntity.status(HttpStatus.OK).body("your image was updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
}
