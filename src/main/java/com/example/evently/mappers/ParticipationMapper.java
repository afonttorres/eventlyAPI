package com.example.evently.mappers;

import com.example.evently.dto.participation.ParticipationRes;
import com.example.evently.mappers.event.EventMapper;
import com.example.evently.models.Participation;
import com.example.evently.models.event.Event;
import com.example.evently.models.user.User;

import javax.servlet.http.Part;
import java.util.List;
import java.util.stream.Collectors;

public class ParticipationMapper {

    public Participation mapReqToParticipation(Event event, User participant){
        var participation = new Participation();
        participation.setEvent(event);
        participation.setParticipant(participant);
        return participation;
    }

    public ParticipationRes mapParticipationToRes(Participation participation){
        var res = new ParticipationRes();
        res.setId(participation.getId());
        res.setEvent(new EventMapper().mapEventToNestedEvent(participation.getEvent()));
        res.setParticipant(new UserMapper().mapUserToNestedUser(participation.getParticipant()));
        return res;
    }

    public List<ParticipationRes> mapMultParticipationsToResList(List<Participation> participations){
        return participations.stream()
                .map(p -> new ParticipationMapper().mapParticipationToRes(p))
                .collect(Collectors.toList());
    }
}
