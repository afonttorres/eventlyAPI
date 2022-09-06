package com.example.evently.controllers;

import com.example.evently.dto.direction.DirectionReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.Direction;
import com.example.evently.services.direction.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class DirectionController {

    DirectionService directionService;

    @Autowired
    public DirectionController(DirectionService directionService) {
        this.directionService = directionService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/directions")
    ResponseEntity<EventRes> create(@RequestBody DirectionReq req){
        return new ResponseEntity<>( directionService.create(req), HttpStatus.OK);
    }
}
