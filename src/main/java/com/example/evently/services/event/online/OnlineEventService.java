package com.example.evently.services.event.online;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.WebUrl;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OnlineEvent;
import com.example.evently.models.user.User;

public interface OnlineEventService {
    EventRes create(EventReq req, User auth);

    OnlineEvent getById(Long id);

    EventRes addLocationToEvent(WebUrl url, OnlineEvent event);
    EventRes createFromOfflineEvent(EventReqUpdate req, Event event);
}
