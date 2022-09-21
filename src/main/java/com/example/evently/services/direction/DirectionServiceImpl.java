package com.example.evently.services.direction;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.direction.DirectionReq;
import com.example.evently.dto.output.Message;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.DirectionMapper;
import com.example.evently.models.Direction;
import com.example.evently.models.EmailDetails;
import com.example.evently.models.Type;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.user.User;
import com.example.evently.repositories.DirectionRepository;
import com.example.evently.services.email.EmailService;
import com.example.evently.services.event.offline.OfflineEventService;
import com.example.evently.services.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectionServiceImpl implements DirectionService{
    DirectionRepository directionRepository;
    OfflineEventService offlineEventService;
    AuthFacade authFacade;
    NotificationService notificationService;

    @Autowired
    public DirectionServiceImpl(DirectionRepository directionRepository, OfflineEventService offlineEventService, AuthFacade authFacade, NotificationService notificationService) {
        this.directionRepository = directionRepository;
        this.offlineEventService = offlineEventService;
        this.authFacade = authFacade;
        this.notificationService = notificationService;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
    }

    @Override
    public List<Direction> getAll() {
        return directionRepository.findAll();
    }

    @Override
    public Direction getById(Long id) {
        var dir = directionRepository.findById(id);
        if(dir.isEmpty())
            throw new NotFoundEx("Direction not found", "D-404");
        return dir.get();
    }

    @Override
    public Direction getByEventId(Long id){
        var event = offlineEventService.getById(id);
        var direction = directionRepository.findByEventId(id).stream().findAny();
        if(direction.isEmpty())
            throw new NotFoundEx("Direction not found", "D-404");
        return direction.get();
    }

    @Override
    public Message create(Long eventId, DirectionReq req) {
        var event = offlineEventService.getById(eventId);
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher add a direction!", "D-001");
        if(!event.getType().equals(Type.OFFLINE))
            throw new BadReqEx("Only offline events can have a direction", "D-002");
        var direction = new DirectionMapper().mapReqToDirection(req, event);
        this.resetDirection(event);
        directionRepository.save(direction);
        offlineEventService.addLocationToEvent(direction, event);
        notificationService.createLocationNotification(direction.toString(), event);
        return new Message("Direction "+direction.toString()+" added to event "+event.getTitle()+" !");
    }


    @Override
    public Message delete(Long eventId) {
        var event = offlineEventService.getById(eventId);
        this.resetDirection(event);
        offlineEventService.addLocationToEvent(null, event);
        return new Message("Direction deleted!");
    }

    private void resetDirection(OfflineEvent event){
        var eventDirections = directionRepository.findByEventId(event.getId());
        directionRepository.deleteAll(eventDirections);
    }


}
