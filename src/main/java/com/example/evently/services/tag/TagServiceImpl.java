package com.example.evently.services.tag;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.tag.PostMultTagsReq;
import com.example.evently.dto.tag.TagReq;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.models.Tag;
import com.example.evently.repositories.TagRepository;
import com.example.evently.services.event.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    TagRepository tagRepository;
    EventService eventService;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, EventService eventService) {
        this.tagRepository = tagRepository;
        this.eventService = eventService;
    }


    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getById(Long id) {
        var tag = tagRepository.findById(id);
        if(tag.isEmpty()) throw new NotFoundEx("Tag Not Found", "T-404");
        return tag.get();
    }

    @Override
    public Tag create(TagReq req) {
        if(tagRepository.findByName(req.getName()).isPresent()){
            return tagRepository.findByName(req.getName()).get();
        }
        return tagRepository.save(new Tag(req.getName()));
    }

    @Override
    public Message addEventTags(Long eventId, PostMultTagsReq req) {
        var tags = Arrays.stream(req.getTags())
                .map(t-> this.create(new TagReq(t)))
                .collect(Collectors.toList());
        eventService.setEventTags(eventId, tags);
        return new Message("Tags added!");
    }

    @Override
    public Message delete(Long eventId, TagReq req) {
        var event = eventService.getCompleteEventById(eventId);
        var tag = tagRepository.findByName(req.getName());
        if(tag.isEmpty())
            throw new NotFoundEx("Tag Not Found", "T-404");
        eventService.deleteEventTag(eventId, tag.get());
        return new Message("Tag "+req.getName()+" deleted!");
    }


}
