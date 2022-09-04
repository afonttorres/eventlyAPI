package com.example.evently.services.event.offline;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.mappers.event.EventMapper;
import com.example.evently.mappers.event.OfflineEventMapper;
import com.example.evently.models.Requirement;
import com.example.evently.models.Tag;
import com.example.evently.models.user.User;
import com.example.evently.repositories.EventRepository;
import com.example.evently.services.event.online.OnlineEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfflineEventServiceImpl implements OfflineEventService {

    EventRepository eventRepository;

    @Autowired
    public OfflineEventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventRes create(EventReq req, User auth, List<Tag> tags){
        var event = new OfflineEventMapper().mapReqToOffEvent(req, tags,auth);
        eventRepository.save(event);
        return new EventMapper().mapEventToRes(event);
    }

}
