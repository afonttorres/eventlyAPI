package com.example.evently.services.event;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.EventMapper;
import com.example.evently.models.Direction;
import com.example.evently.models.Tag;
import com.example.evently.models.Event;
import com.example.evently.models.User;
import com.example.evently.repositories.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;

    AuthFacade authFacade;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            AuthFacade authFacade
    ) {
        this.eventRepository = eventRepository;
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

    public Event getCompleteEventById(Long id){
        var event = eventRepository.findById(id);
        if(event.isEmpty()) throw new NotFoundEx("Event Not Found", "E-404");
        return event.get();
    }

    @Override
    public EventRes create(EventReq req) {
        var auth = this.getAuth();
        if(req.getType() == null) throw new BadReqEx("Type can't be empty!", "T-001");
        if(!req.getType().equals("offline") && !req.getType().equals("online")) throw new BadReqEx("Wrong type!", "T-002");
        var event = new EventMapper().mapReqToEvent(req,auth);
        eventRepository.save(event);
        return new EventMapper().mapEventToRes(event);
    }

    @Override
    public EventRes delete(Long id) {
        var event = this.getCompleteEventById(id);
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher is allowed to delete it!", "T-002");
        var res = new EventMapper().mapEventToRes(event);
        eventRepository.delete(event);
        return res;
    }

    @Override
    public Event setEventTags(Long id, List<Tag> tags){
        var event = this.getCompleteEventById(id);
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher can add tags!", "T-002");
        Set<Tag> eventTags = event.getTags().stream().map(t-> t).collect(Collectors.toSet());
        tags.forEach(t -> eventTags.add(t));
        event.setTags(eventTags.stream().map(t-> t).collect(Collectors.toList()));
        return eventRepository.save(event);
    }

    @Override
    public Event deleteEventTag(Long id, Tag tag) {
        var event = this.getCompleteEventById(id);
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher can add tags!", "T-002");
        event.getTags().remove(tag);
        return eventRepository.save(event);
    }

    @Override
    public EventRes update(Long id, EventReqUpdate eventReq) {
        var event = this.getCompleteEventById(id);
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher is allowed to update it!", "T-002");
        var updated = new EventMapper().mapReqToExistingEvent(eventReq, event);
        eventRepository.save(updated);
        return new EventMapper().mapEventToRes(updated);
    }

}
