package com.example.evently.services.direction;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.direction.DirectionReq;
import com.example.evently.dto.output.Message;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.DirectionMapper;
import com.example.evently.models.Event;
import com.example.evently.models.Type;
import com.example.evently.models.User;
import com.example.evently.repositories.DirectionRepository;
import com.example.evently.services.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DirectionServiceImpl implements DirectionService{
    DirectionRepository directionRepository;
    EventService eventService;
    AuthFacade authFacade;

    @Autowired
    public DirectionServiceImpl(DirectionRepository directionRepository, EventService eventService, AuthFacade authFacade) {
        this.directionRepository = directionRepository;
        this.eventService = eventService;
        this.authFacade = authFacade;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
    }

    @Override
    public Message create(DirectionReq req) {
        var event = eventService.getCompleteEventById(req.getEventId());
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher add a direction!", "D-001");
        if(!event.getType().equals(Type.OFFLINE))
            throw new BadReqEx("Only offline events can have a direction", "D-002");
        var direction = new DirectionMapper().mapReqToDirection(req, event);
        //comprovar si existeix open maps api
        this.resetDirection(event);
        directionRepository.save(direction);
        return new Message("Direction "+direction.toString()+" created!");
    }

    private void resetDirection(Event event){
        directionRepository.findAll().forEach(d -> {
            if(d.getEvent() == event){
                directionRepository.delete(d);
            }
        });
    }

    @Override
    public boolean deleteById(Long id){
        directionRepository.deleteById(id);
        return true;
    }


}
