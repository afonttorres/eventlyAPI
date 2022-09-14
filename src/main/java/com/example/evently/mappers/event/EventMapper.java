package com.example.evently.mappers.event;


import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.mappers.*;
import com.example.evently.models.Direction;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.event.OnlineEvent;
import lombok.Data;

import java.text.SimpleDateFormat;
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
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        res.setRequirements(new RequirementMapper().mapMultRequirementsToStringArr(event.getRequirements()));
        res.setDate(new SimpleDateFormat("dd-MM-yyyy").format(event.getDate()));
        res.setHour(new SimpleDateFormat("HH:mm").format(event.getDate().getTime()));
        res.setImages(new ImageMapper().mapImagesToArray(event.getImages()));
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setLocation(this.defineLocation(event));
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public EventRes mapEventToRes(OfflineEvent event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        res.setRequirements(new RequirementMapper().mapMultRequirementsToStringArr(event.getRequirements()));
        res.setDate(new SimpleDateFormat("dd-MM-yyyy").format(event.getDate()));
        res.setHour(new SimpleDateFormat("HH:mm").format(event.getDate().getTime()));
        res.setImages(new ImageMapper().mapImagesToArray(event.getImages()));
        res.setType(event.getType().toString());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        res.setLocation(this.defineDirection(event));
        return res;
    }

    public EventRes mapEventToRes(OnlineEvent event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        res.setRequirements(new RequirementMapper().mapMultRequirementsToStringArr(event.getRequirements()));
        res.setDate(new SimpleDateFormat("dd-MM-yyyy").format(event.getDate()));
        res.setHour(new SimpleDateFormat("HH:mm").format(event.getDate().getTime()));
        res.setImages(new ImageMapper().mapImagesToArray(event.getImages()));
        res.setType(event.getType().toString());
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setLocation(this.defineWeb(event));
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public List<EventRes> mapMultipleEventsToRes(List<Event> events){
        List<EventRes> res = new ArrayList<>();
        events.stream().forEach(e -> res.add(this.mapEventToRes(e)));
        return res;
    }

    public Event mapReqToExistingEvent(EventReqUpdate eventReq, Event event) {
        event.setTitle(eventReq.getTitle());
        event.setDescription(eventReq.getDescription());
        event.setDate(new DateMapper().convertLocalDateTimeToDate(eventReq.getDate()));
        return event;
    }

    public String defineLocation(Event event){
        String location = "";
        if(event.getLocation() != null){
            location = event.getLocation();
        }
        return location;
    }

    public String defineDirection(OfflineEvent event){
        String direction = "";
        if(event.getDirection() != null){
            direction = event.getDirection().toString();
        }
        return direction;
    }

    public String defineWeb(OnlineEvent event){
        String web = "";
        if(event.getWebUrl() != null){
            web = event.getWebUrl().getUrl();
        }
        return web;
    }

}
