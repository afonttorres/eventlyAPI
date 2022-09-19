package com.example.evently.controllers;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.weburl.WebUrlReq;
import com.example.evently.models.WebUrl;
import com.example.evently.services.weburl.WebUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class WebUrlController {

    WebUrlService webUrlService;

    @Autowired
    public WebUrlController(WebUrlService webUrlService) {
        this.webUrlService = webUrlService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/webUrl")
    ResponseEntity<List<WebUrl>> getAll(){
        return new ResponseEntity<>(webUrlService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/events/{id}/webUrl")
    ResponseEntity<WebUrl> getByEventId(@PathVariable Long id){
        return new ResponseEntity<>(webUrlService.getByEventId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/webUrl/{id}")
    ResponseEntity<WebUrl> getById(@PathVariable Long id){
        return new ResponseEntity<>(webUrlService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/events/{id}/webUrl")
    ResponseEntity<Message> create(@PathVariable Long id, @RequestBody WebUrlReq req){
        return new ResponseEntity<>(webUrlService.create(id, req), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/events/{id}/webUrl")
    ResponseEntity<Message> delete(@PathVariable Long id){
        return new ResponseEntity<>(webUrlService.delete(id), HttpStatus.OK);
    }

}