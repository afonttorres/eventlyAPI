package com.example.evently.services.participation;

import com.example.evently.dto.participation.ParticipationRes;
import com.example.evently.models.Participation;

import java.util.List;

public interface ParticipationService {
    ParticipationRes create(Long id);

    List<ParticipationRes> getAll();
}
