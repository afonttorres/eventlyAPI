package com.example.evently.mappers.event;

import com.example.evently.dto.event.req.EventJsonReq;
import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.models.*;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.user.User;

import java.util.List;

public class OfflineEventMapper {
    public OfflineEvent mapReqToOffEvent(EventReq eventReq, User auth){
        var event = new OfflineEvent();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setDate(eventReq.getDate());
        event.setPublisher(auth);
        return event;
    }

    public OfflineEvent mapJsonReqToOffEvent(EventJsonReq eventReq, List<Tag> tags, User auth){
        var event = new OfflineEvent();
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setDate(eventReq.getDate());
        event.setTags(tags);
        event.setPublisher(auth);
        return event;
    }

    public OfflineEvent mapOnlineToOfflineEvent(EventReqUpdate req, Event event) {
        OfflineEvent offline = new OfflineEvent();
        offline.setId(event.getId());
        offline.setTitle(req.getTitle());
        offline.setDescription(req.getDescription());
        offline.setDate(req.getDate());
        offline.setTags(event.getTags());
        offline.setPublisher(event.getPublisher());
        return offline;

    }
}
