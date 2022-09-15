package com.example.evently.controllers;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.participation.ParticipationRes;
import com.example.evently.services.participation.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ParticipationController {

    ParticipationService participationService;

    @Autowired
    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/participations")
    ResponseEntity<List<ParticipationRes>> getAll(){
        return new ResponseEntity<>(participationService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/participations/{id}")
    ResponseEntity<ParticipationRes> getById(@PathVariable Long id){
        return new ResponseEntity<>(participationService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}/participations")
    ResponseEntity<List<ParticipationRes>> getByUserId(@PathVariable Long id){
        return new ResponseEntity<>(participationService.findByParticipantId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/participations/{id}")
    ResponseEntity<Message> delete(@PathVariable Long id){
        return new ResponseEntity<>(participationService.delete(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/events/{id}/participations")
    ResponseEntity<Message> create(@PathVariable Long id){
        return new ResponseEntity<>(participationService.create(id), HttpStatus.OK);
    }

    @GetMapping("/events/{id}/participations")
    ResponseEntity<List<ParticipationRes>> getByEventId(@PathVariable Long id){
        return new ResponseEntity<>(participationService.getByEventId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/events/{id}/unjoin")
    ResponseEntity<Message> unjoin(@PathVariable Long id){
        return new ResponseEntity<>(participationService.unjoin(id), HttpStatus.OK);
    }
}
