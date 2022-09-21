package com.example.evently.controllers;

import com.example.evently.dto.direction.DirectionReq;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.dto.output.Message;
import com.example.evently.models.Direction;
import com.example.evently.services.direction.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class DirectionController {

    DirectionService directionService;

    @Autowired
    public DirectionController(DirectionService directionService) {
        this.directionService = directionService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/directions")
    ResponseEntity<List<Direction>> getAll(){
        return new ResponseEntity<>( directionService.getAll(), HttpStatus.OK);
    }


    @GetMapping("/events/{id}/directions")
    ResponseEntity<Direction> getByEventId(@PathVariable Long id){
        return new ResponseEntity<>( directionService.getByEventId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/directions/{id}")
    ResponseEntity<Direction> getById(@PathVariable Long id){
        return new ResponseEntity<>( directionService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/events/{id}/directions")
    ResponseEntity<Message> create(@PathVariable Long id,@Valid @RequestBody DirectionReq req){
        return new ResponseEntity<>( directionService.create(id, req), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("events/{id}/directions")
    ResponseEntity<Message> delete(@PathVariable Long id){
        return new ResponseEntity<>( directionService.delete(id), HttpStatus.OK);
    }
}
