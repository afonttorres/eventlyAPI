package com.example.evently.services.participation;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.output.Message;
import com.example.evently.dto.participation.ParticipationRes;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.ParticipationMapper;
import com.example.evently.models.Participation;
import com.example.evently.models.user.User;
import com.example.evently.repositories.ParticipationRepository;
import com.example.evently.services.event.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationServiceImpl implements ParticipationService{
    ParticipationRepository participationRepository;
    EventService eventService;
    AuthFacade authFacade;

    @Autowired
    public ParticipationServiceImpl(ParticipationRepository participationRepository, EventService eventService, AuthFacade authFacade) {
        this.participationRepository = participationRepository;
        this.eventService = eventService;
        this.authFacade = authFacade;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
    }

    public Participation getCompleteParticipation(Long id){
        var participation = participationRepository.findById(id);
        if(participation.isEmpty()) throw new NotFoundEx("Participation not found", "P-404");
        return participation.get();
    }



    @Override
    public Message create(Long id) {
        var auth = this.getAuth();
        var event = eventService.getCompleteEventById(id);
        if(event.isParticipant(auth))
            throw new BadReqEx("Already participating!", "P-001");
        var part = new ParticipationMapper().mapReqToParticipation(event, auth);
        participationRepository.save(part);
        return new Message("You've just joined "+event.getTitle()+"!");
    }

    @Override
    public List<ParticipationRes> getAll(){
        return new ParticipationMapper().mapMultParticipationsToResList(participationRepository.findAll());
    }



    @Override
    public ParticipationRes getById(Long id) {
        var participation = this.getCompleteParticipation(id);
        return new ParticipationMapper().mapParticipationToRes(participation);
    }

    @Override
    public List<ParticipationRes> getByEventId(Long id) {
        return new ParticipationMapper().mapMultParticipationsToResList(participationRepository.findByEventId(id));
    }

    @Override
    public Message delete(Long id) {
        var participation = this.getCompleteParticipation(id);
        if(participation.getParticipant() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only participants can decide if stop participating", "P-002");
        return new Message("You are no longer participating in "+participation.getEvent().getTitle()+".");
    }
}
