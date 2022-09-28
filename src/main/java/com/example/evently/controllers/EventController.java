package com.example.evently.controllers;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.services.event.event.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value="", tags={"Event"})
public class EventController {

    EventService eventService;


    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    @ApiOperation(value = "Get all events")
    ResponseEntity<List<EventRes>>getAll(){
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/events/{id}")
    @ApiOperation(value = "Get a single event by its id")
    ResponseEntity<EventRes> getEventById(@PathVariable Long id){
        return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/events")
    @ApiOperation(value = "Create an event")
    ResponseEntity<EventRes>create(@Valid @RequestBody EventReq eventReq){
        return new ResponseEntity<>(eventService.create(eventReq), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/events/{id}")
    @ApiOperation(value = "Delete an event")
    ResponseEntity<EventRes> delete(@PathVariable Long id){
        return new ResponseEntity<>(eventService.delete(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/events/{id}")
    @ApiOperation(value = "Update an event")
    ResponseEntity<EventRes> update(@PathVariable Long id,@Valid  @RequestBody EventReqUpdate eventReq){
        return new ResponseEntity<>(eventService.update(id, eventReq), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/joined-events")
    @ApiOperation(value = "Get authenticated user joined events")
    ResponseEntity<List<EventRes>> getUserJoinedEvents(){
        return new ResponseEntity<>(eventService.getUserJoinedEvents(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/published-events")
    @ApiOperation(value = "Get authenticated user published events")
    ResponseEntity<List<EventRes>> getAuthPublishedEvents(){
        return new ResponseEntity<>(eventService.getAuthPublishedEvents(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}/events")
    @ApiOperation(value = "Get user published events by its id")
    ResponseEntity<List<EventRes>> getUserPublishedEvents(@PathVariable Long id){
        return new ResponseEntity<>(eventService.getUserPublishedEvents(id), HttpStatus.OK);
    }

//    events?search=${tag}
    @GetMapping(value="/events", params ="tag")
    @ApiOperation(value = "Get events by tag")
    ResponseEntity<List<EventRes>> getByTag(
            @ApiParam(
                    name =  "tag",
                    type = "String",
                    value = "tag",
                    example = "esport",
                    required = true)
            @RequestParam String tag){

        return new ResponseEntity<>(eventService.getByTag(tag), HttpStatus.OK);
    }

//    events?search=${type}
    @GetMapping(value="/events", params ="type")
    @ApiOperation(value = "Get events by type")
    ResponseEntity<List<EventRes>> getByType(
            @ApiParam(
                    name =  "type",
                    type = "String",
                    value = "type",
                    example = "offline",
                    required = true)
            @RequestParam String type){
        return new ResponseEntity<>(eventService.getByType(type), HttpStatus.OK);
    }

    //    events?search=${search}
    @GetMapping(value="/events", params ="search")
    @ApiOperation(value = "Get events by search")
    ResponseEntity<List<EventRes>> getBySearch(
            @ApiParam(
                    name =  "search",
                    type = "String",
                    value = "search",
                    example = "bicicleta",
                    required = true)@RequestParam String search){
        return new ResponseEntity<>(eventService.getBySearch(search), HttpStatus.OK);
    }
}
