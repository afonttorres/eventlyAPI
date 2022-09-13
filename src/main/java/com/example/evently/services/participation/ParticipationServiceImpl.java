package com.example.evently.services.participation;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.participation.ParticipationRes;
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

    @Override
    public ParticipationRes create(Long id) {
        var auth = this.getAuth();
        var event = eventService.getCompleteEventById(id);
        var part = new ParticipationMapper().mapReqToParticipation(event, auth);
        participationRepository.save(part);
        return new ParticipationMapper().mapParticipationToRes(part);
    }

    @Override
    public List<ParticipationRes> getAll(){
        return new ParticipationMapper().mapMultParticipationsToResList(participationRepository.findAll());
    }
}
