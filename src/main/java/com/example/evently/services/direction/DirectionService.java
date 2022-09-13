package com.example.evently.services.direction;

import com.example.evently.dto.direction.DirectionReq;
import com.example.evently.dto.output.Message;
import com.example.evently.models.Direction;

import java.util.List;

public interface DirectionService {
    List<Direction> getAll();

    Direction getById(Long id);

    Direction getByEventId(Long id);

    Message create(Long eventId, DirectionReq req);

    Message delete(Long eventId);


}
