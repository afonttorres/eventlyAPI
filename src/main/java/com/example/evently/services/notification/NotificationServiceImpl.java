package com.example.evently.services.notification;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.output.Message;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.models.EmailDetails;
import com.example.evently.models.Notification;
import com.example.evently.models.event.Event;
import com.example.evently.models.user.User;
import com.example.evently.repositories.NotificationRepository;
import com.example.evently.services.email.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService{


    AuthFacade authFacade;
    NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(AuthFacade authFacade,
                                   NotificationRepository notificationRepository) {
        this.authFacade = authFacade;
        this.notificationRepository = notificationRepository;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
    }


    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getAuthNotificatons() {
        var auth = this.getAuth();
        return notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .filter( n-> n.getNotified() == auth)
                .collect(Collectors.toList());
    }

    @Override
    public void createDeleteNotification(Event event) {
        String description = "The "+event.beautified()+" has been deleted!";
        String subject = "Event deleted in Evently App!";
        var notifications = event.getParticipants()
                .stream()
                .map(p -> new Notification(subject, description, p.getParticipant()))
                .collect(Collectors.toList());
        notificationRepository.saveAll(notifications);
        //aqui
        event.getParticipants().forEach(p ->
                MailService.sendSimpleMail(new EmailDetails(p.getParticipant().getEmail(), description,subject)));
    }

    @Override
    public void createJoinNotification(Event event) {
        var auth = this.getAuth();
        String description = "You've joined "+event.beautified();
        String subject = "You've joined an event in Evently App!";
        notificationRepository.save(new Notification(subject, description, auth));
        MailService.sendSimpleMail(new EmailDetails(auth.getEmail(), description,subject));
        //aqui
    }

    @Override
    public void createUpdatedNotification(Event event, String eventBeautified, Event updated) {
        String subject = "Event modified in Evently App!";
        String description = "The "+eventBeautified+" has been updated to: "+updated.beautified()+
                ". Check it out at http://localhost:3000/events/"+updated.getId()+".";
        var notifications = event.getParticipants()
                .stream()
                .map(p -> new Notification(subject, description, p.getParticipant()))
                .collect(Collectors.toList());
        notificationRepository.saveAll(notifications);
        //aqui
        event.getParticipants().forEach(p ->
                MailService.sendSimpleMail(new EmailDetails(p.getParticipant().getEmail(), description,subject)));
    }

    @Override
    public void createLocationNotification(String location, Event event) {
        String description = "The "+event.getTitle()+" has a new location: "+location+". Check it out at http://localhost:3000/events/"+event.getId()+".";
        String subject = "Event location modified in Evently App!";
        var notifications = event.getParticipants()
                .stream()
                .map(p -> new Notification(subject, description, p.getParticipant()))
                .collect(Collectors.toList());
        notificationRepository.saveAll(notifications);
        //aqui
        event.getParticipants().forEach(p ->
                MailService.sendSimpleMail(new EmailDetails(p.getParticipant().getEmail(), description,subject)));
    }

    @Override
    public Message delete(Long id) {
        var notification = notificationRepository.findById(id);
        if(notification.isEmpty())
            throw new NotFoundEx("Notification not found", "N-404");
        if(notification.get().getNotified() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only notified users can delete their notifications", "N-001");
        notificationRepository.delete(notification.get());
        return new Message("Notification with subject "+notification.get().getSubject()+" deleted.");
    }

    @Override
    public Message toggleCheck(Long id) {
        var notification = notificationRepository.findById(id);
        if(notification.isEmpty())
            throw new NotFoundEx("Notification not found", "N-404");
        if(notification.get().getNotified() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only notified users can check their notifications", "N-001");
        notification.get().setChecked(!notification.get().isChecked());
        notificationRepository.save(notification.get());
        if(notification.get().isChecked()){
            return new Message("Notification with subject "+notification.get().getSubject()+" checked.");
        }
        return new Message("Notification with subject "+notification.get().getSubject()+" unchecked.");
    }




}
