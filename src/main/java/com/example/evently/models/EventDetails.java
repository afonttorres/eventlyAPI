package com.example.evently.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "details")
@AllArgsConstructor
@NoArgsConstructor
public class EventDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonSerialize
    private Set<Requirement> requirements = new HashSet<>();


    @OneToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;
}
