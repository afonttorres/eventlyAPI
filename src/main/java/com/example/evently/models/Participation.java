package com.example.evently.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "participants")
@AllArgsConstructor
@NoArgsConstructor
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private User participant;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;
}
