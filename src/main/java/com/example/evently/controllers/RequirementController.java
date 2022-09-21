package com.example.evently.controllers;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.requirement.RequirementReq;
import com.example.evently.models.Requirement;
import com.example.evently.services.requirement.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class RequirementController {

    RequirementService requirementService;

    @Autowired
    public RequirementController(RequirementService requirementService) {
        this.requirementService = requirementService;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/requirements")
    public ResponseEntity<List<Requirement>> getAll(){
        return new ResponseEntity<>(requirementService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/requirements/{id}")
    public ResponseEntity<Requirement> getById(@PathVariable Long id){
        return new ResponseEntity<>(requirementService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/events/{id}/requirements")
    public ResponseEntity<List<Requirement>> getByEventId(@PathVariable Long id){
        return new ResponseEntity<>(requirementService.getByEventId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("events/{id}/requirements")
    public ResponseEntity<Message> create(@PathVariable Long id,@Valid @RequestBody RequirementReq req){
        return new ResponseEntity<>(requirementService.create(id, req), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("events/{id}/requirements")
    public ResponseEntity<Message> delete(@PathVariable Long id,@Valid  @RequestBody RequirementReq req){
        return new ResponseEntity<>(requirementService.delete(id, req), HttpStatus.OK);
    }

}
