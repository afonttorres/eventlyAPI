package com.example.evently.mappers;

import com.example.evently.dto.requirement.RequirementReq;
import com.example.evently.models.Requirement;
import com.example.evently.models.event.Event;

import java.util.ArrayList;
import java.util.List;

public class RequirementMapper {
    public String[] mapMultRequirementsToStringArr(List<Requirement> requirements){
        var res = new ArrayList<>();
        requirements.forEach(r -> res.add(r.getName()));
        return res.toArray(new String[0]);
    }

    public Requirement mapReqToRequirment(RequirementReq req, Event event){
        var res = new Requirement();
        res.setName(req.getName());
        res.setEvent(event);
        return res;
    }
}
