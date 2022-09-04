package com.example.evently.mappers.event;


import com.example.evently.dto.event.res.EventRes;
import com.example.evently.mappers.TagMapper;
import com.example.evently.mappers.UserMapper;
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
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        event.getRequirements().forEach(r-> System.out.println("event req from mapper: "+ r.getName()));
        res.setRequirements(event.getRequirements());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public EventRes mapEventToRes(OfflineEvent event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
//        res.setTags(event.getTags());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        event.getRequirements().forEach(r-> System.out.println("event req from mapper: "+ r.getName()));
        res.setRequirements(event.getRequirements());
        res.setType(event.getType().toString());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public EventRes mapEventToRes(OnlineEvent event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
//        res.setTags(event.getTags());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        event.getRequirements().forEach(r-> System.out.println("event req from mapper: "+ r.getName()));
        res.setRequirements(event.getRequirements());
        res.setType(event.getType().toString());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }


    public List<EventRes> mapMultipleEventsToRes(List<Event> events){
        List<EventRes> res = new ArrayList<>();
        events.stream().forEach(e -> res.add(this.mapEventToRes(e)));
        return res;
    }
}
