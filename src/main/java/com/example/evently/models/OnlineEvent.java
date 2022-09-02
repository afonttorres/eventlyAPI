package com.example.evently.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
public class OnlineEvent extends Event{
    String url;

    @OneToOne
    @JoinColumn(name = "event_type")
    private EventType type;
}
