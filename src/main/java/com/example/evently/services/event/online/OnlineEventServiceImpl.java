package com.example.evently.services.event.online;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.mappers.event.EventMapper;
import com.example.evently.mappers.event.OnlineEventMapper;
import com.example.evently.models.Requirement;
import com.example.evently.models.Tag;
import com.example.evently.models.user.User;
import com.example.evently.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlineEventServiceImpl implements OnlineEventService {

    EventRepository eventRepository;

    @Autowired
    public OnlineEventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventRes create(EventReq req, User auth) {
        var event = new OnlineEventMapper().mapReqToOnEvent(req, auth);
        eventRepository.save(event);
        return new EventMapper().mapEventToRes(event);
    }
}
