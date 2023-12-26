package com.example.eventticketmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
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

    @ManyToMany(mappedBy = "events")
    private Set<UserEntity> participants = new HashSet<>();


    public void setImageFromMultipartFile(MultipartFile file) {
        if (file.getSize() != 0) {
            try {
                this.image = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTicketToEvent(TicketEntity ticketEntity) {
        this.listOfTickets.add(ticketEntity);
    }
}