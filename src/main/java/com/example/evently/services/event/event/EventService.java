package com.example.evently.services.event.event;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.Tag;
import com.example.evently.models.event.Event;
import com.example.evently.models.user.User;

import java.util.List;

public interface EventService {


    List<EventRes> getAll();
    EventRes getEventById(Long id);
    EventRes create(EventReq eventReq);
    EventRes update(Long id, EventReqUpdate eventReq);
    EventRes delete(Long id);
    Event setEventTags(Long id, List<Tag> tags);
    Event deleteEventTag(Long id, Tag tag);
    List<EventRes> getUserJoinedEvents();
    List<EventRes>  getAuthPublishedEvents();
    List<EventRes> getUserPublishedEvents(Long id);
    List<EventRes> getByTag(String tag);
    List<EventRes> getByType(String type);
    List<EventRes> getBySearch(String search);
    Event getCompleteEventById(Long id);
    List<Event> getUserJoinedEvents(User auth);


}
