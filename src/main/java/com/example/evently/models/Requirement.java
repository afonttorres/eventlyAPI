package com.example.evently.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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


    private String requirement;

    @ManyToOne
    @JoinColumn(name = "event_details_id")
    @JsonSerialize
    private EventDetails eventDetails;
}
