package com.example.evently.services.requirement;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.requirement.RequirementReq;
import com.example.evently.models.Requirement;

import java.util.List;

public interface RequirementService {
    List<Requirement> getAll();
    Requirement getById(Long id);
    Message create(Long eventId, RequirementReq req);
    List<Requirement> getByEventId(Long id);

    Message delete(Long eventId, RequirementReq req);
}
