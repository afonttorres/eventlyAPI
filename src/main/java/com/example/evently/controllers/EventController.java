package com.example.evently.controllers;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.services.event.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class EventController {

    EventService eventService;


    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    ResponseEntity<List<EventRes>>getAll(){
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/events/{id}")
    ResponseEntity<EventRes> getEventById(@PathVariable Long id){
        return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/events")
    ResponseEntity<EventRes>create(@RequestBody EventReq eventReq){
        return new ResponseEntity<>(eventService.create(eventReq), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/events/{id}")
    ResponseEntity<EventRes> delete(@PathVariable Long id){
        return new ResponseEntity<>(eventService.delete(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/events/{id}")
    ResponseEntity<EventRes> update(@PathVariable Long id, @RequestBody EventReqUpdate eventReq){
        return new ResponseEntity<>(eventService.update(id, eventReq), HttpStatus.OK);
    }
}
