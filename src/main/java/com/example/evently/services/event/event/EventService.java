package com.example.evently.services.event.event;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.event.Event;

import java.util.List;

public interface EventService {


    List<EventRes> getAll();
    EventRes getEventById(Long id);
    Event getCompleteEventById(Long id);

    EventRes create(EventReq eventReq);

    EventRes delete(Long id);
}
