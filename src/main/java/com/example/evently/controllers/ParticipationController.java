package com.example.evently.controllers;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.participation.ParticipationRes;
import com.example.evently.services.participation.ParticipationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value="", tags={"Participation"})
public class ParticipationController {

    ParticipationService participationService;

    @Autowired
    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @GetMapping("/participations")
    @ApiOperation(value = "Get all participations")
    ResponseEntity<List<ParticipationRes>> getAll(){
        return new ResponseEntity<>(participationService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/participations/{id}")
    @ApiOperation(value = "Get a single participation by its id")
    ResponseEntity<ParticipationRes> getById(@PathVariable Long id){
        return new ResponseEntity<>(participationService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}/participations")
    @ApiOperation(value = "Get user participations by its id")
    ResponseEntity<List<ParticipationRes>> getByUserId(@PathVariable Long id){
        return new ResponseEntity<>(participationService.findByParticipantId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/participations/{id}")
    @ApiOperation(value = "Delete a participation by its id")
    ResponseEntity<Message> delete(@PathVariable Long id){
        return new ResponseEntity<>(participationService.delete(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/events/{id}/participations")
    @ApiOperation(value = "Create an event participation (join)")
    ResponseEntity<Message> create(@PathVariable Long id){
        return new ResponseEntity<>(participationService.create(id), HttpStatus.OK);
    }

    @GetMapping("/events/{id}/participations")
    @ApiOperation(value = "Get event participations")
    ResponseEntity<List<ParticipationRes>> getByEventId(@PathVariable Long id){
        return new ResponseEntity<>(participationService.getByEventId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/events/{id}/unjoin")
    @ApiOperation(value = "Delete an event participation (unjoin)")
    ResponseEntity<Message> unjoin(@PathVariable Long id){
        return new ResponseEntity<>(participationService.unjoin(id), HttpStatus.OK);
    }
}
