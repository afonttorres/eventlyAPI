package com.example.evently.services.direction;

import com.example.evently.dto.direction.DirectionReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.Direction;

public interface DirectionService {
    EventRes create(DirectionReq req);
}
