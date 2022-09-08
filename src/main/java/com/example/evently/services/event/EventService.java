package com.example.evently.services.event;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.Tag;
import com.example.evently.models.Event;

import java.util.List;

public interface EventService {


    List<EventRes> getAll();
    EventRes getEventById(Long id);
    Event getCompleteEventById(Long id);

    EventRes create(EventReq eventReq);

    EventRes delete(Long id);

    Event setEventTags(Long id, List<Tag> tags);
    Event deleteEventTag(Long id, Tag tag);

    EventRes update(Long id, EventReqUpdate eventReq);
}
