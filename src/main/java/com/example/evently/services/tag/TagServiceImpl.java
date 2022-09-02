package com.example.evently.services.tag;

import com.example.evently.dto.tag.TagReq;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.models.Tag;
import com.example.evently.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
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
        if(this.getAll().stream().filter(c-> c.getName().equals(req.getName())).findAny().isPresent())
            throw new BadReqEx("Tag Already Exist", "C-001");
        var tag = new Tag();
        tag.setName(req.getName());
        return tagRepository.save(tag);
    }

    @Override
    public Tag findByName(String catName){
        if(!tagRepository.existsByName(catName)) throw new NotFoundEx("Tag Not Found", "C-404");
        return tagRepository.findByName(catName).get();
    }

    @Override
    public List<Tag> findCategoriesByName(String[] cats){
        return Arrays.stream(cats).map(c -> this.findByName(c)).collect(Collectors.toList());
    }

}
