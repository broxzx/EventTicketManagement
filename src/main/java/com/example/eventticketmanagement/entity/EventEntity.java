package com.example.eventticketmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@Entity
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startsAt;

    @Column(name = "venue", nullable = false)
    private String venue;

    @Column(nullable = false, name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "event")
    private List<TicketEntity> listOfTickets;


    public void addTicketToEvent(TicketEntity ticketEntity) {
        this.listOfTickets.add(ticketEntity);
    }
}