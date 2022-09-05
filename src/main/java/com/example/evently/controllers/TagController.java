package com.example.evently.controllers;

import com.example.evently.dto.tag.TagReq;
import com.example.evently.models.Tag;
import com.example.evently.services.tag.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TagController {

    TagService categoryService;


    public TagController(TagService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/tags")
    ResponseEntity<List<Tag>> getCategories(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tags/{id}")
    ResponseEntity<Tag> getCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/tags")
    ResponseEntity<Tag> createCategory(@RequestBody TagReq req){
        return new ResponseEntity<>(categoryService.create(req), HttpStatus.OK);
    }


}
