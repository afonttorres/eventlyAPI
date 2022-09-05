package com.example.evently.fakers.tag;

import com.example.evently.dto.event.res.EventRes;
import com.example.evently.dto.tag.PostMultTagsReq;
import com.example.evently.dto.tag.TagReq;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.event.EventMapper;
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
        if(tag.isEmpty()) throw new NotFoundEx("Tag Not Found", "C-404");
        return tag.get();
    }

    @Override
    public Tag create(TagReq req) {
        if(tagRepository.findAll().stream().filter(t-> t.getName().equals(req.getName())).findFirst().isPresent()){
            return tagRepository.findAll().stream().filter(t-> t.getName().equals(req.getName())).findFirst().get();
        }
        return tagRepository.save(new Tag(req.getName()));
    }

    @Override
    public EventRes addTagsToEvent(Long eventId, PostMultTagsReq req) {
        var tags = Arrays.stream(req.getTags())
                .map(t-> this.create(new TagReq(t)))
                .collect(Collectors.toList());
        return new EventMapper().mapEventToRes(eventService.addTags(eventId, tags));
    }



}
