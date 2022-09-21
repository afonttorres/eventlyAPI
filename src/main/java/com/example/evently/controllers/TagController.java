package com.example.evently.controllers;

import com.example.evently.dto.event.res.EventRes;
import com.example.evently.dto.output.Message;
import com.example.evently.dto.tag.PostMultTagsReq;
import com.example.evently.dto.tag.TagReq;
import com.example.evently.models.Tag;
import com.example.evently.services.tag.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class TagController {

    TagService tagService;


    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    ResponseEntity<List<Tag>> getTags(){
        return new ResponseEntity<>(tagService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tags/{id}")
    ResponseEntity<Tag> getTag(@PathVariable Long id){
        return new ResponseEntity<>(tagService.getById(id), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
//    @PostMapping("/tags")
//    ResponseEntity<Tag> create(@RequestBody TagReq req){
//        return new ResponseEntity<>(tagService.create(req), HttpStatus.OK);
//    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/events/{id}/tags")
    ResponseEntity<Message> addTagsToEvent(@PathVariable Long id,@Valid @RequestBody PostMultTagsReq req){
        return new ResponseEntity<>(tagService.addEventTags(id, req), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/events/{id}/tags")
    ResponseEntity<Message> delete(@PathVariable Long id,@Valid  @RequestBody TagReq req){
        return new ResponseEntity<>(tagService.delete(id, req), HttpStatus.OK);
    }


}
