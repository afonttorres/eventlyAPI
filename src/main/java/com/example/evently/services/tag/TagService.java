package com.example.evently.services.tag;

import com.example.evently.dto.tag.TagReq;
import com.example.evently.models.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<Tag> getAll();

    Tag getById(Long id);

    Tag create(TagReq req);
    Tag findByName(String catName);
    List<Tag> findCategoriesByName(String[] cats);

    List<Tag> getMultById(Long[] ids);
}
