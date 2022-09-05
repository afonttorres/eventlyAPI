package com.example.evently.mappers.event;


import com.example.evently.dto.event.res.EventRes;
import com.example.evently.mappers.RequirementMapper;
import com.example.evently.mappers.TagMapper;
import com.example.evently.mappers.UserMapper;
import com.example.evently.models.Direction;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.event.OnlineEvent;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventMapper {
    public EventRes mapEventToRes(Event event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
        res.setType(event.getType().toString());
//        res.setTags(event.getTags());
//        res.setRequirements(event.getRequirements());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        res.setRequirements(new RequirementMapper().mapMultRequirementsToStringArr(event.getRequirements()));

        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setLocation(event.getLocation());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public EventRes mapEventToRes(OfflineEvent event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
//        res.setTags(event.getTags());
//        res.setRequirements(event.getRequirements());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        res.setRequirements(new RequirementMapper().mapMultRequirementsToStringArr(event.getRequirements()));
        res.setType(event.getType().toString());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        var direction = new Direction();
        res.setLocation(direction.toString());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public EventRes mapEventToRes(OnlineEvent event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
//        res.setTags(event.getTags());
//        res.setRequirements(event.getRequirements());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        res.setRequirements(new RequirementMapper().mapMultRequirementsToStringArr(event.getRequirements()));
        res.setType(event.getType().toString());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setLocation(event.getWebUrl());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }


    public List<EventRes> mapMultipleEventsToRes(List<Event> events){
        List<EventRes> res = new ArrayList<>();
        events.stream().forEach(e -> res.add(this.mapEventToRes(e)));
        return res;
    }
}
