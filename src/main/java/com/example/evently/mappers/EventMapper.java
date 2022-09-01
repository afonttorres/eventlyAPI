package com.example.evently.mappers;


import com.example.evently.dto.events.req.EventReq;
import com.example.evently.dto.events.res.EventRes;
import com.example.evently.models.Event;
import com.example.evently.models.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventMapper {
    public Event mapReqToEvent(EventReq eventReq, User auth){
        var event = new Event();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setPublisher(auth);
        return event;
    }

    public EventRes mapEventToRes(Event event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
        res.setCategories(event.getCategories());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public List<EventRes> mapMultipleEventsToRes(List<Event> events){
        List<EventRes> res = new ArrayList<>();
        events.stream().forEach(e -> res.add(this.mapEventToRes(e)));
        return res;
    }
}
