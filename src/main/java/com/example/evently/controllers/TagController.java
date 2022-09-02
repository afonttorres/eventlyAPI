package com.example.evently.controllers;

import com.example.evently.dto.tag.TagReq;
import com.example.evently.models.Tag;
import com.example.evently.services.tag.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TagController {

    TagService categoryService;


    public TagController(TagService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    ResponseEntity<List<Tag>> getAll(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    ResponseEntity<Tag> getATag(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/categories")
    ResponseEntity<Tag> create(@RequestBody TagReq req){
        return new ResponseEntity<>(categoryService.create(req), HttpStatus.OK);
    }


}
