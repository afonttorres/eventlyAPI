package com.example.evently.services.event;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.EventMapper;
import com.example.evently.models.EventType;
import com.example.evently.models.OfflineEvent;
import com.example.evently.models.User;
import com.example.evently.repositories.EventRepository;
import com.example.evently.repositories.EventTypeRepository;
import com.example.evently.services.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    TagService tagService;
    EventTypeRepository typeRepository;
    AuthFacade authFacade;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, TagService tagService, AuthFacade authFacade,
                            EventTypeRepository typeRepository) {
        this.eventRepository = eventRepository;
        this.tagService = tagService;
        this.authFacade = authFacade;
        this.typeRepository = typeRepository;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
    }


    @Override
    public List<EventRes> getAll() {
        return new EventMapper().mapMultipleEventsToRes(eventRepository.findAll());
    }

    @Override
    public EventRes getEventById(Long id) {
        var event = eventRepository.findById(id);
        if(event.isEmpty()) throw new NotFoundEx("Event Not Found", "E-404");
        return new EventMapper().mapEventToRes(event.get());
    }

    @Override
    public EventRes create(EventReq eventReq) {
        var auth = this.getAuth();
        var type = typeRepository.findById(1).get();
        System.out.println("fins a type , type: "+type.nameToString());
        System.out.println(eventReq.getTags());
        var tags = tagService.getMultById(eventReq.getTags());
        System.out.println("fins a tags");
        tags.forEach(t -> System.out.println(t.getName()));
        var event = new EventMapper().mapReqToOffEvent(eventReq, type, tags,auth);
        eventRepository.save(event);
        return new EventMapper().mapEventToOffRes(event);
    }
}
