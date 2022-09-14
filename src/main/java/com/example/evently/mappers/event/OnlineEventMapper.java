package com.example.evently.mappers.event;

import com.example.evently.dto.event.req.EventJsonReq;
import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.mappers.DateMapper;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OnlineEvent;
import com.example.evently.models.Tag;
import com.example.evently.models.user.User;

import java.util.List;

public class OnlineEventMapper {
    public OnlineEvent mapReqToOnEvent(EventReq eventReq, User auth){
        var event = new OnlineEvent();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setDate(new DateMapper().convertLocalDateTimeToDate(eventReq.getDate()));
        event.setPublisher(auth);
        return event;
    }

    public OnlineEvent mapJsonReqToOnEvent(EventJsonReq eventReq, List<Tag> tags, User auth){
        var event = new OnlineEvent();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setDate(eventReq.getDate());
        event.setTags(tags);
        event.setPublisher(auth);
        return event;
    }

    public OnlineEvent mapOfflineToOnlineEvent(EventReqUpdate req, Event event) {
        OnlineEvent online = new OnlineEvent();
        online.setId(event.getId());
        online.setTitle(req.getTitle());
        online.setDescription(req.getDescription());
        online.setDate(new DateMapper().convertLocalDateTimeToDate(req.getDate()));
        online.setTags(event.getTags());
        online.setPublisher(event.getPublisher());
        return online;
    }
}
