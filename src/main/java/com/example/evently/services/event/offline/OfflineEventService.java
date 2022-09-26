package com.example.evently.services.event.offline;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.Direction;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.user.User;

public interface OfflineEventService {
    Event create(EventReq req, User auth);

    OfflineEvent getById(Long eventId);

    EventRes addLocationToEvent(Direction direction, OfflineEvent event);

    Event createFromOnlineEvent(EventReqUpdate req, Event event);
}
