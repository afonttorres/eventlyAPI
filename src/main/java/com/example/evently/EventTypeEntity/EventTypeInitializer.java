package com.example.evently.EventTypeEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventTypeInitializer {
    private EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeInitializer(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    public void setEventTypes(){
        if(!eventTypeRepository.findAll().isEmpty()){
            return;
        }

        List<EventType> types = List.of(
                new EventType(1, EventType.TypeName.OFFLINE),
                new EventType(2, EventType.TypeName.ONLINE)
        );

        eventTypeRepository.saveAll(types);
        System.out.println(eventTypeRepository.findAll().size()+" types saved!");
        eventTypeRepository.findAll().forEach(t -> System.out.println(t.toString()));
    }



}
