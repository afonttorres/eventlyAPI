package com.example.evently.services.direction;

import com.example.evently.dto.direction.DirectionReq;
import com.example.evently.dto.output.Message;

public interface DirectionService {
    Message create(DirectionReq req);

    boolean deleteById(Long id);
}
