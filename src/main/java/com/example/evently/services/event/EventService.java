package com.example.evently.services.event;

import com.example.evently.dto.event.res.EventRes;

import java.util.List;

public interface EventService {


    List<EventRes> getAll();
    EventRes getEventById(Long id);
//
//    EventRes create(EventReq eventReq);

}
