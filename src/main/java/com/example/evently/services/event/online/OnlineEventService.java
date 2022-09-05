package com.example.evently.services.event.online;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.Requirement;
import com.example.evently.models.Tag;
import com.example.evently.models.user.User;

import java.util.List;

public interface OnlineEventService {
    EventRes create(EventReq req, User auth);
}
