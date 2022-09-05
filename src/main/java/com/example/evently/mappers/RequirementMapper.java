package com.example.evently.mappers;

import com.example.evently.models.Requirement;

import java.util.ArrayList;
import java.util.List;

public class RequirementMapper {
    public String[] mapMultRequirementsToStringArr(List<Requirement> requirements){
        var res = new ArrayList<>();
        requirements.forEach(r -> res.add(r.getName()));
        return res.toArray(new String[0]);
    }
}
