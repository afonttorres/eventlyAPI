package com.example.evently.mappers.event;


import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.dto.event.res.NestedEventRes;
import com.example.evently.mappers.*;
import com.example.evently.models.Direction;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.event.OnlineEvent;
import com.example.evently.models.user.User;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        res.setDate(new DateMapper().convertDateToStringDate(event.getDate()));
        res.setHour(new DateMapper().convertDateToStringHour(event.getDate()));
        res.setImages(new ImageMapper().mapImagesToArray(event.getImages()));
        res.setParticipantsCount(event.participantsCount());
        res.setLocation(this.defineLocation(event));
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public EventRes mapEventToRes(Event event, User auth){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
        res.setType(event.getType().toString());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        res.setRequirements(new RequirementMapper().mapMultRequirementsToStringArr(event.getRequirements()));
        res.setDate(new DateMapper().convertDateToStringDate(event.getDate()));
        res.setHour(new DateMapper().convertDateToStringHour(event.getDate()));
        res.setImages(new ImageMapper().mapImagesToArray(event.getImages()));
        res.setParticipantsCount(event.participantsCount());
        res.setLocation(this.defineLocation(event));
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        res.setParticipant(event.isParticipant(auth));
        return res;
    }

    public EventRes mapEventToRes(OfflineEvent event){
        var res = new EventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        res.setRequirements(new RequirementMapper().mapMultRequirementsToStringArr(event.getRequirements()));
        res.setDate(new DateMapper().convertDateToStringDate(event.getDate()));
        res.setHour(new DateMapper().convertDateToStringHour(event.getDate()));
        res.setImages(new ImageMapper().mapImagesToArray(event.getImages()));
        res.setType(event.getType().toString());
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
        res.setDate(new DateMapper().convertDateToStringDate(event.getDate()));
        res.setHour(new DateMapper().convertDateToStringHour(event.getDate()));
        res.setImages(new ImageMapper().mapImagesToArray(event.getImages()));
        res.setType(event.getType().toString());
        res.setParticipantsCount(event.participantsCount());
        res.setLocation(this.defineWeb(event));
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        return res;
    }

    public NestedEventRes mapEventToNestedEvent(Event event){
        var res = new NestedEventRes();
        res.setId(event.getId());
        res.setTitle(event.getTitle());
        res.setDescription(event.getDescription());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        res.setDate(new DateMapper().convertDateToStringDate(event.getDate()));
        res.setHour(new DateMapper().convertDateToStringHour(event.getDate()));
        res.setType(event.getType().toString());
        res.setParticipantsCount(event.participantsCount());
        res.setImages((new ImageMapper().mapImagesToArray(event.getImages())));
        res.setLocation(this.defineLocation(event));
        return res;
    }

    public List<EventRes> mapMultipleEventsToRes(List<Event> events){
        List<EventRes> res = new ArrayList<>();
        events.stream().forEach(e -> res.add(this.mapEventToRes(e)));
        return res;
    }

    public List<EventRes> mapMultipleEventsToRes(List<Event> events, User auth){
        List<EventRes> res = new ArrayList<>();
        events.stream().forEach(e -> res.add(this.mapEventToRes(e, auth)));
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
