package com.example.evently.models;

import com.example.evently.models.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "requirements")
@AllArgsConstructor
@NoArgsConstructor
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    private String name;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;
}
