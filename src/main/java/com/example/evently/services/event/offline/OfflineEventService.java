package com.example.evently.services.event.offline;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.Direction;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.user.User;
import com.example.evently.repositories.event.OfflineRepository;

import java.util.Optional;

public interface OfflineEventService {
    EventRes create(EventReq req, User auth);

    OfflineEvent getById(Long eventId);

    EventRes addLocationToEvent(Direction direction, OfflineEvent event);
}
