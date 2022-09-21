package com.example.evently.controllers;

import com.example.evently.dto.output.Message;
import com.example.evently.models.Notification;
import com.example.evently.services.notification.NotificationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class NotificationController {

    NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping("/notifications")
    ResponseEntity<List<Notification>> getAll(){
        return new ResponseEntity<List<Notification>>(notificationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/auth-notifications")
    ResponseEntity<List<Notification>> getAuthNotificatons(){
        return new ResponseEntity<List<Notification>>(notificationService.getAuthNotificatons(), HttpStatus.OK);
    }

    @DeleteMapping("/auth-notifications/{id}")
    ResponseEntity<Message> delete(@PathVariable Long id){
        return new ResponseEntity<Message>(notificationService.delete(id), HttpStatus.OK);
    }

    @PatchMapping("/auth-notifications/{id}/check")
    ResponseEntity<Message> toggleCheck(@PathVariable Long id){
        return new ResponseEntity<Message>(notificationService.toggleCheck(id), HttpStatus.OK);
    }
}
