package com.example.evently.services.event;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.EventMapper;
import com.example.evently.models.User;
import com.example.evently.repositories.EventRepository;
import com.example.evently.services.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    TagService tagService;
    AuthFacade authFacade;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, TagService tagService, AuthFacade authFacade) {
        this.eventRepository = eventRepository;
        this.tagService = tagService;
        this.authFacade = authFacade;
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

//    @Override
//    public EventRes create(EventReq eventReq) {
//        var auth = this.getAuth();
//        var categories = categoryService.createMultCategories(eventReq.getCategories());
//        categories.forEach(c-> System.out.println(c));
//        var event = new EventMapper().mapReqToEvent(eventReq, categories,auth);
//        eventRepository.save(event);
//        return new EventMapper().mapEventToRes(event);
//    }
}
