package com.example.evently.services.event.online;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.event.EventMapper;
import com.example.evently.mappers.event.OnlineEventMapper;
import com.example.evently.models.WebUrl;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OnlineEvent;
import com.example.evently.models.user.User;
import com.example.evently.repositories.event.EventRepository;
import com.example.evently.repositories.event.OnlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineEventServiceImpl implements OnlineEventService {

    EventRepository eventRepository;
    OnlineRepository onlineRepository;

    @Autowired
    public OnlineEventServiceImpl(EventRepository eventRepository, OnlineRepository onlineRepository) {
        this.eventRepository = eventRepository;
        this.onlineRepository = onlineRepository;
    }


    @Override
    public OnlineEvent getById(Long id) {
        var event = onlineRepository.findById(id);
        if(event.isEmpty())
            throw new NotFoundEx("Online event not found", "E-404");
        return event.get();
    }

    @Override
    public Event create(EventReq req, User auth) {
        var event = new OnlineEventMapper().mapReqToOnEvent(req, auth);
        eventRepository.save(event);
        onlineRepository.save(event);
        return event;
    }

    @Override
    public EventRes addLocationToEvent(WebUrl url, OnlineEvent event) {
        event.setLocation(this.defineLocation(url));
        eventRepository.save(event);
        onlineRepository.save(event);
        return new EventMapper().mapEventToRes(event);
    }

    @Override
    public Event createFromOfflineEvent(EventReqUpdate req, Event event) {
        eventRepository.delete(event);
        return eventRepository.save( new OnlineEventMapper().mapOfflineToOnlineEvent(req, event));
    }

    public String defineLocation(WebUrl webUrl){
        String location = "";
        if(webUrl != null) location =webUrl.getUrl();
        return location;
    }


}
