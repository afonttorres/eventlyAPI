package com.example.evently.mappers.event;

import com.example.evently.dto.event.req.EventJsonReq;
import com.example.evently.dto.event.req.EventReq;
import com.example.evently.models.Requirement;
import com.example.evently.models.event.OnlineEvent;
import com.example.evently.models.Tag;
import com.example.evently.models.user.User;

import java.util.List;

public class OnlineEventMapper {
    public OnlineEvent mapReqToOnEvent(EventReq eventReq, List<Tag> tags, User auth){
        var event = new OnlineEvent();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setTags(tags);
        event.setPublisher(auth);
        return event;
    }
    public OnlineEvent mapReqToOnEvent(EventReq eventReq, User auth){
        var event = new OnlineEvent();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setPublisher(auth);
        return event;
    }

    public OnlineEvent mapJsonReqToOnEvent(EventJsonReq eventReq, List<Tag> tags, User auth){
        var event = new OnlineEvent();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setTags(tags);
        event.setPublisher(auth);
        return event;
    }
}
