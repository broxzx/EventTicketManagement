package com.example.eventticketmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Boolean bought;

    @ManyToOne
    private EventEntity event;

    @ManyToOne
    private UserEntity user;

    @PrePersist
    private void init() {
        this.bought = false;
    }
}
