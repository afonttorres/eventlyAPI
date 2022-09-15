package com.example.evently.services.event.event;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.TypeMapper;
import com.example.evently.mappers.event.EventMapper;
import com.example.evently.models.Tag;
import com.example.evently.models.Type;
import com.example.evently.models.event.Event;
import com.example.evently.models.user.User;
import com.example.evently.repositories.event.EventRepository;
import com.example.evently.services.event.offline.OfflineEventService;
import com.example.evently.services.event.online.OnlineEventService;
import com.example.evently.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    OfflineEventService offlineService;
    OnlineEventService onlineService;
    AuthFacade authFacade;

    UserService userService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            AuthFacade authFacade,
                            OnlineEventService onlineService,
                            OfflineEventService offlineService,
                            UserService userService
    ) {
        this.eventRepository = eventRepository;
        this.authFacade = authFacade;
        this.onlineService = onlineService;
        this.offlineService = offlineService;
        this.userService = userService;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
    }

    @Override
    public Event getCompleteEventById(Long id){
        var event = eventRepository.findById(id);
        if(event.isEmpty()) throw new NotFoundEx("Event Not Found", "E-404");
        return event.get();
    }
    @Override
    public List<Event> getUserJoinedEvents(User auth){
        return eventRepository.findByParticipantId(auth.getId());
    }

    private void validateType(String type){
        if(type == null ||type == "") throw new BadReqEx("Type can't be empty!", "T-001");
        if(!type.equals("offline") && !type.equals("online")) throw new BadReqEx("Wrong type!", "T-002");
    }

    private EventRes assingType(EventReq req, User auth){
        this.validateType(req.getType());
        if(req.getType().equals("online")) return onlineService.create(req, auth);
        return offlineService.create(req, auth);
    }

    private EventRes changeType(EventReqUpdate req, Event event){
        this.validateType(req.getType());
        if(req.getType().equals("online")){
            return onlineService.createFromOfflineEvent(req, event);
        }
        return offlineService.createFromOnlineEvent(req, event);
    }


    @Override
    public List<EventRes> getAll() {
        var auth = authFacade.getAuthUser();
        var events = eventRepository.findAll();
        if(auth.isEmpty()) return new EventMapper().mapMultipleEventsToRes(events);
        return new EventMapper().mapMultipleEventsToResAuth(events, auth.get());
    }

    @Override
    public EventRes getEventById(Long id) {
        var event = eventRepository.findById(id);
        var auth = authFacade.getAuthUser();
        if(event.isEmpty()) throw new NotFoundEx("Event Not Found", "E-404");
        if(auth.isEmpty()) return new EventMapper().mapEventToRes(event.get());
        return new EventMapper().mapEventToResAuth(event.get(), auth.get());
    }

    @Override
    public EventRes create(EventReq eventReq) {
        var auth = this.getAuth();
        return this.assingType(eventReq, auth);
    }

    @Override
    public EventRes delete(Long id) {
        var event = this.getCompleteEventById(id);
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher is allowed to delete it!", "T-002");
        var res = new EventMapper().mapEventToRes(event);
        eventRepository.delete(event);
        //email to notify event has been deleted
        System.out.println("EVENT DELETED EMAIL");
        return res;
    }

    @Override
    public EventRes update(Long id, EventReqUpdate req) {
        var event = this.getCompleteEventById(id);
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher is allowed to update it!", "T-002");
        if(!req.getType().equals(event.getType().toString().toLowerCase())){
            var newEvent = this.changeType(req, event);
            //email to notify modification
            System.out.println("EVENT MODIFIED EMAIL");
            return newEvent;
        }
        System.out.println(req);
        var updated = new EventMapper().mapReqToExistingEvent(req, event);
        eventRepository.save(updated);
        //email to notify modification
        System.out.println("EVENT MODIFIED EMAIL");
        return new EventMapper().mapEventToRes(updated);
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
        var tags = event.getTags().stream().filter(t -> t!= tag).collect(Collectors.toList());
        event.setTags(tags);
        return eventRepository.save(event);
    }

    @Override
    public List<EventRes> getUserJoinedEvents() {
        var auth = this.getAuth();
        return new EventMapper().mapMultipleEventsToResAuth(this.getUserJoinedEvents(auth), auth);
    }

    @Override
    public List<EventRes> getAuthPublishedEvents() {
        var auth = this.getAuth();
        return new EventMapper().mapMultipleEventsToResAuth(eventRepository.findByPublisherId(auth.getId()), auth);
    }

    @Override
    public List<EventRes> getUserPublishedEvents(Long id) {
        var user = userService.getById(id);
        var auth = authFacade.getAuthUser();
        if(auth.isEmpty()) return new EventMapper().mapMultipleEventsToRes(eventRepository.findByPublisherId(id));
        return new EventMapper().mapMultipleEventsToResAuth(eventRepository.findByPublisherId(id), auth.get());
    }

    @Override
    public List<EventRes> getByTag(String tag) {
        var auth = authFacade.getAuthUser();
        if(tag.isEmpty() ||tag == null ||tag == "")
            throw new BadReqEx("Tag can't be empty!", "E-001");
        if(auth.isEmpty()) return new EventMapper().mapMultipleEventsToRes(eventRepository.findByTag(tag));
        return new EventMapper().mapMultipleEventsToResAuth(eventRepository.findByTag(tag), auth.get());
    }

    @Override
    public List<EventRes> getByType(String type) {
        var auth = authFacade.getAuthUser();
        this.validateType(type);
        var search = new TypeMapper().stringToType(type);
        if(auth.isEmpty()) return new EventMapper().mapMultipleEventsToRes(eventRepository.findByType(search));
        return new EventMapper().mapMultipleEventsToResAuth(eventRepository.findByType(search), auth.get());
    }

}
