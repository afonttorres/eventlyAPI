package com.example.evently.services.event;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.event.EventMapper;
import com.example.evently.mappers.event.OfflineEventMapper;
import com.example.evently.models.Tag;
import com.example.evently.models.user.User;
import com.example.evently.repositories.EventRepository;
import com.example.evently.EventTypeEntity.EventTypeRepository;
import com.example.evently.services.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    TagService tagService;
    EventTypeRepository typeRepository;
    OffEventServiceImpl offlineService;
    OnEventServiceImpl onlineService;


    AuthFacade authFacade;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            TagService tagService,
                            AuthFacade authFacade,
                            OnEventServiceImpl onlineService,
                            OffEventServiceImpl offlineService
//                            EventTypeRepository typeRepository
    ) {
        this.eventRepository = eventRepository;
        this.tagService = tagService;
        this.authFacade = authFacade;
        this.onlineService = onlineService;
        this.offlineService = offlineService;
//        this.typeRepository = typeRepository;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
    }


    @Override
    public List<EventRes> getAll() {
        eventRepository.findAll().forEach(e -> System.out.println(e.getType().toString()));
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
        var tags = tagService.getMultById(eventReq.getTags());
        return this.assingType(eventReq, auth, tags);
    }

    private EventRes assingType(EventReq req, User auth, List<Tag> tags){
        if(req.getType() == null) throw new BadReqEx("Type can't be empty!", "T-001");
        if(req.getType().equals("online")) return onlineService.create(req, auth, tags);
        return offlineService.create(req, auth, tags);
    }
}
