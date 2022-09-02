package com.example.evently.fakers;

import com.example.evently.models.EventType;
import com.example.evently.repositories.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

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
