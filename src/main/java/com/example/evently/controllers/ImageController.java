package com.example.evently.controllers;



import com.example.evently.dto.output.Message;
import com.example.evently.dto.image.ImageReqDelete;
import com.example.evently.dto.image.ImageRes;
import com.example.evently.models.Image;
import com.example.evently.services.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class ImageController {

    ImageService imageService;


    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getAll(){
        List<Image> images = imageService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Image> getById(@PathVariable Long id){
        Image image = imageService.findById(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping("/events/{id}/images")
    public ResponseEntity<ImageRes> upload(@RequestParam MultipartFile multipartFile, @PathVariable Long id) throws IOException {
        return new ResponseEntity<>(imageService.upload(multipartFile, id), HttpStatus.OK);
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(imageService.deleteById(id), HttpStatus.OK);
    }

    @DeleteMapping("/images")
    public ResponseEntity<Message> deleteByUrl(@RequestBody ImageReqDelete req) throws IOException {
        return new ResponseEntity<>(imageService.deleteByUrl(req), HttpStatus.OK);
    }
}
