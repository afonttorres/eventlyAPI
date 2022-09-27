package com.example.evently.controllers;



import com.example.evently.dto.output.Message;
import com.example.evently.dto.image.ImageReqDelete;
import com.example.evently.dto.image.ImageRes;
import com.example.evently.models.Image;
import com.example.evently.services.image.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value="", tags={"Image"})
public class ImageController {

    ImageService imageService;


    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/images")
    @ApiOperation(value = "Get all images")
    public ResponseEntity<List<Image>> getAll(){
        List<Image> images = imageService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/images/{id}")
    @ApiOperation(value = "Get an image by its id")
    public ResponseEntity<Image> getById(@PathVariable Long id){
        Image image = imageService.findById(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/events/{id}/images")
    @ApiOperation(value = "Upload event image")
    public ResponseEntity<ImageRes> upload(@RequestParam MultipartFile multipartFile, @PathVariable Long id) throws IOException {
        System.out.println(multipartFile+" "+id);
        return new ResponseEntity<>(imageService.upload(multipartFile, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/images/{id}")
    @ApiOperation(value = "Delete image by its id")
    public ResponseEntity<Message> deleteById(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(imageService.deleteById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/images")
    @ApiOperation(value = "Delete image by its url")
    public ResponseEntity<Message> deleteByUrl(@Valid @RequestBody ImageReqDelete req) throws IOException {
        return new ResponseEntity<>(imageService.deleteByUrl(req), HttpStatus.OK);
    }
}
