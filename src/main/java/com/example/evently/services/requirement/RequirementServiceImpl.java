package com.example.evently.services.requirement;

import com.example.evently.dto.requirement.RequirementReq;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.models.Requirement;
import com.example.evently.repositories.RequirementRepository;
import com.example.evently.services.event.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequirementServiceImpl implements RequirementService{
    RequirementRepository requirementRepository;
    EventService eventService;

    @Autowired
    public RequirementServiceImpl(RequirementRepository requirementRepository, EventService eventService) {
        this.requirementRepository = requirementRepository;
        this.eventService = eventService;
    }


    @Override
    public List<Requirement> getAll() {
        return requirementRepository.findAll();
    }

    @Override
    public Requirement getById(Long id) {
        var requirement = requirementRepository.findById(id);
        if(requirement.isEmpty()) throw new NotFoundEx("Requirement Not Found", "R-404");
        return requirement.get();
    }

    @Override
    public Requirement create(RequirementReq req) {
        System.out.println(req);
        if(requirementRepository.findByNameInEvent(req.getEventId(), req.getName()).isPresent())
            throw new BadReqEx("Requirement already exists!", "R-001");
        var event = eventService.getCompleteEventById(req.getEventId());
        //if auth != event.publisher ex
        System.out.println(event.getTitle());
        var requirement = new Requirement();
        requirement.setName(req.getName());
        requirement.setEvent(event);
        System.out.println(requirement.getName()+" saved!");
        return requirementRepository.save(requirement);
    }

    @Override
    public List<Requirement> getMultById(Long[] requirementIds) {
        List<Requirement> res = new ArrayList<>();
        for(Long id: requirementIds){
            res.add(requirementRepository.findById(id).get());
        }
        return res;
    }

    @Override
    public List<Requirement> getByEventId(Long id) {
        return requirementRepository.findByEventId(id);
    }
}
