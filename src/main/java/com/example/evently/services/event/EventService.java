package com.example.evently.services.event;

import com.example.evently.dto.events.req.EventReq;
import com.example.evently.dto.events.res.EventRes;

import java.util.List;

public interface EventService {


    List<EventRes> getAll();
    EventRes getEventById(Long id);

    EventRes create(EventReq eventReq);
}
