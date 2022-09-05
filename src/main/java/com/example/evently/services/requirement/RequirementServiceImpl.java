package com.example.evently.services.requirement;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.requirement.RequirementReq;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.models.Requirement;
import com.example.evently.models.Role;
import com.example.evently.models.user.User;
import com.example.evently.repositories.RequirementRepository;
import com.example.evently.services.event.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequirementServiceImpl implements RequirementService{
    RequirementRepository requirementRepository;
    EventService eventService;
    AuthFacade authFacade;

    @Autowired
    public RequirementServiceImpl(RequirementRepository requirementRepository,
                                  EventService eventService,
                                  AuthFacade authFacade
    ) {
        this.requirementRepository = requirementRepository;
        this.eventService = eventService;
        this.authFacade = authFacade;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
    }
    @Override
    public List<Requirement> getAll() {
        return requirementRepository.findAll();
    }

    @Override
    public Requirement getById(Long id) {
        var requirement = requirementRepository.findById(id);
        if(requirement.isEmpty())
            throw new NotFoundEx("Requirement Not Found", "R-404");
        return requirement.get();
    }

    @Override
    public Requirement create(RequirementReq req) {
        if(requirementRepository.findByNameInEvent(req.getEventId(), req.getName()).stream().findAny().isPresent())
            throw new BadReqEx("Requirement already exists!", "R-001");
        var event = eventService.getCompleteEventById(req.getEventId());
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher can add requirements!", "R-003");
        var requirement = new Requirement();
        requirement.setName(req.getName());
        requirement.setEvent(event);
        return requirementRepository.save(requirement);
    }

    @Override
    public List<Requirement> getByEventId(Long id) {
        return requirementRepository.findByEventId(id);
    }
}
