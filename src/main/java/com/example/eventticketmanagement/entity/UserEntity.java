package com.example.eventticketmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "password cannot be shorter than 8 symbols")
    private String password;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false, name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    @OneToMany
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private List<TicketEntity> listOfTickets;


    @PrePersist
    public void init() {
        this.role = Role.USER;
    }
}
