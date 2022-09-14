package com.example.evently.services.participation;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.participation.ParticipationRes;
import com.example.evently.models.Participation;

import java.util.List;

public interface ParticipationService {
    Message create(Long id);

    List<ParticipationRes> getAll();

    ParticipationRes getById(Long id);

    List<ParticipationRes> getByEventId(Long id);
    List<ParticipationRes> findByParticipantId(Long id);
    Message delete(Long id);
    Message unjoin(Long id);
}
