package com.example.evently.controllers;

import com.example.evently.dto.output.Message;
import com.example.evently.models.Notification;
import com.example.evently.services.notification.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value="", tags={"Notification"})
public class NotificationController {

    NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping("/notifications")
    @ApiOperation(value = "Get all notifications")
    ResponseEntity<List<Notification>> getAll(){
        return new ResponseEntity<List<Notification>>(notificationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/auth-notifications")
    @ApiOperation(value = "Get authenticated user notifications")
    ResponseEntity<List<Notification>> getAuthNotificatons(){
        return new ResponseEntity<List<Notification>>(notificationService.getAuthNotificatons(), HttpStatus.OK);
    }

    @DeleteMapping("/auth-notifications/{id}")
    @ApiOperation(value = "Delete a notification")
    ResponseEntity<Message> delete(@PathVariable Long id){
        return new ResponseEntity<Message>(notificationService.delete(id), HttpStatus.OK);
    }

    @PatchMapping("/auth-notifications/{id}/check")
    @ApiOperation(value = "Toggle notification check")
    ResponseEntity<Message> toggleCheck(@PathVariable Long id){
        return new ResponseEntity<Message>(notificationService.toggleCheck(id), HttpStatus.OK);
    }
}
