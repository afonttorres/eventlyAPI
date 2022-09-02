package com.example.evently.mappers;


import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class EventMapper {

    public Event mapReqToEvent(EventReq eventReq, User auth){
        var event = new Event();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setTags(event.getTags());
        event.setPublisher(auth);
        return event;
    }

    public EventRes mapEventToRes(Event event){
        System.out.println(event.getClass().getDeclaredFields());
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
        res.setTags(event.getTags());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public EventRes mapEventToRes(OfflineEvent event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
        res.setTags(event.getTags());
        res.setType(event.getType().nameToString());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public OfflineEvent mapReqToOffEvent(EventReq eventReq, EventType type, List<Tag>tags, User auth){
        var event = new OfflineEvent();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setType(type);
        event.setTags(tags);
        event.setPublisher(auth);
        return event;
    }

    public List<EventRes> mapMultipleEventsToRes(List<Event> events){
        List<EventRes> res = new ArrayList<>();
        events.stream().forEach(e -> res.add(this.mapEventToRes(e)));
        return res;
    }
}
