package com.example.evently.services.event.offline;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.event.EventMapper;
import com.example.evently.mappers.event.OfflineEventMapper;
import com.example.evently.models.Direction;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.user.User;
import com.example.evently.repositories.event.EventRepository;
import com.example.evently.repositories.event.OfflineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OfflineEventServiceImpl implements OfflineEventService {

    EventRepository eventRepository;
    OfflineRepository offlineRepository;

    @Autowired
    public OfflineEventServiceImpl(EventRepository eventRepository, OfflineRepository offlineRepository) {
        this.eventRepository = eventRepository;
        this.offlineRepository = offlineRepository;
    }

    @Override
    public EventRes create(EventReq req, User auth){
        var event = new OfflineEventMapper().mapReqToOffEvent(req, auth);
        eventRepository.save(event);
        offlineRepository.save(event);
        return new EventMapper().mapEventToRes(event);
    }

    @Override
    public OfflineEvent getById(Long eventId) {
        var event = offlineRepository.findById(eventId);
        if(event.isEmpty())
            throw new NotFoundEx("Event Not Found", "E-404");
        return event.get();
    }

    @Override
    public EventRes addLocationToEvent(Direction direction, OfflineEvent event) {
        event.setLocation(direction.toString());
        eventRepository.save(event);
        offlineRepository.save(event);
        return new EventMapper().mapEventToRes(event);
    }

}
