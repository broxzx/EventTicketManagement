package com.example.eventticketmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
    private Date createdAt;

    @Column(name = "venue", nullable = false)
    private String venue;

    @Column(nullable = false, name = "description")
    @Lob
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "event")
    private List<TicketEntity> listOfTickets;

    @ManyToMany(mappedBy = "events")
    private Set<UserEntity> participants = new HashSet<>();

    @PrePersist
    public void init() { // TODO: нужно ли оно тут, или переместить в DTO?
        createdAt = new Date();
    }


    public void setImageFromMultipartFile(MultipartFile file) {
        if (file.getSize() != 0) {
            try {
                this.image = file.getBytes();
            } catch (IOException e) {
            }
        }
    }
}