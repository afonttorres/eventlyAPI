package com.example.evently.models;

import com.example.evently.models.event.OnlineEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    String url;

    @OneToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    OnlineEvent event;
}
