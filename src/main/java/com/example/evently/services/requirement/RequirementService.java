package com.example.evently.services.requirement;

import com.example.evently.dto.requirement.RequirementReq;
import com.example.evently.models.Requirement;

import java.util.List;

public interface RequirementService {
    List<Requirement> getAll();
    Requirement getById(Long id);
    Requirement create(RequirementReq req);
    List<Requirement> getByEventId(Long id);

    Boolean delete(RequirementReq req);
}
