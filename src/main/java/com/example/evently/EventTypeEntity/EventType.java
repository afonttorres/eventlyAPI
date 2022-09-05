package com.example.evently.EventTypeEntity;

import javax.persistence.*;

@Entity
@Table(name = "event_types")
public class EventType {
    public enum TypeName{
        OFFLINE,
        ONLINE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EventType.TypeName name;

    public String nameToString(){
        if(this.name == TypeName.OFFLINE){
            return "offline";
        }
        return "online";
    }

    public EventType(){

    }

    public EventType(Integer id, EventType.TypeName name) {
        this.id = id;
        this.name = name;
    }

    public EventType(EventType.TypeName name){
        this.name = name;
    }
}
