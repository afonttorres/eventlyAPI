package com.example.evently.mappers;


import com.example.evently.dto.event.req.EventJsonReq;
import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.dto.event.res.EventRes;
import com.example.evently.models.event.Event;
import com.example.evently.models.Tag;
import com.example.evently.models.Type;
import com.example.evently.models.user.User;
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
//        res.setTags(event.getTags());
//        res.setRequirements(event.getRequirements());
        res.setTags(new TagMapper().mapMultTagsToStringArr(event.getTags()));
        res.setRequirements(new RequirementMapper().mapMultRequirementsToStringArr(event.getRequirements()));
        res.setDate(new SimpleDateFormat("dd-M-yyyy").format(event.getDate()));
        res.setHour(new SimpleDateFormat("HH:mm").format(event.getDate().getTime()));
        res.setImages(new ImageMapper().mapImagesToArray(event.getImages()));
        res.setParticipants(event.getParticipants());
        res.setParticipantsCount(event.participantsCount());
        res.setPublisher(new UserMapper().mapUserToNestedUser(event.getPublisher()));
        res.setLocation(this.defineLocation(event));
        return res;
    }


    private String defineLocation(Event event){
        String location ="";
        if(event.getDirection() != null ){
            location = event.getDirection().toString();
        }
        if(event.getUrl() != null) {
            location = event.getUrl().getUrl();
        }
        return location;
    }

    private Type defineType(String reqType){
        Type type;
        switch (reqType){
            case "online" :{
                type = Type.ONLINE;
                break;
            }
            case "offline":{
                type = Type.OFFLINE;
                break;
            }
            default :{
                type = Type.OFFLINE;
            }
        }
        return type;
    }

    public Event mapReqToEvent(EventReq req, User auth){
        Event event = new Event();
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setDate(req.getDate());
        event.setType(this.defineType(req.getType()));
        event.setPublisher(auth);
        return event;
    }

    public Event mapReqToEvent(EventJsonReq req, List<Tag> tags, User auth) {
        var event = new Event();
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setDate(req.getDate());
        event.setType(this.defineType(req.getType()));
        event.setTags(tags);
        event.setPublisher(auth);
        return event;
    }

    public List<EventRes> mapMultipleEventsToRes(List<Event> events){
        List<EventRes> res = new ArrayList<>();
        events.stream().forEach(e -> res.add(this.mapEventToRes(e)));
        return res;
    }

    public Event mapReqToExistingEvent(EventReqUpdate req, Event event) {
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setDate(req.getDate());
        event.setType(this.defineType(req.getType()));
        return event;
    }
}
