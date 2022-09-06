package com.example.evently.mappers;

import com.example.evently.dto.direction.DirectionReq;
import com.example.evently.models.Direction;
import com.example.evently.models.event.OfflineEvent;

public class DirectionMapper {
    public Direction mapReqToDirection(DirectionReq directionReq, OfflineEvent event){
        Direction direction = new Direction();
        direction.setCountry(directionReq.getCountry());
        direction.setProvince(directionReq.getProvince());
        direction.setCity(directionReq.getCity());
        direction.setStreet(directionReq.getStreet());
        direction.setBuilding(directionReq.getBuilding());
        direction.setDoor(directionReq.getDoor());
        direction.setEvent(event);
        return direction;
    }
}
