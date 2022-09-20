package com.example.evently.services.notification;

import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.output.Message;
import com.example.evently.models.Notification;
import com.example.evently.models.event.Event;

import java.util.List;

public interface NotificationService {
    List<Notification> getAll();

    List<Notification> getAuthNotificatons();
    void createDeleteNotification(Event event);
    void createJoinNotification(Event event);

    void createUpdatedNotification(Event event, String beautified, Event updated);
    Message delete(Long id);
}
