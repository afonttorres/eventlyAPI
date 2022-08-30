package com.example.evently.services.participation;

import com.example.evently.repositories.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipationServiceImpl implements ParticipationService{
    ParticipationRepository participationRepository;

    @Autowired
    public ParticipationServiceImpl(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }



}
