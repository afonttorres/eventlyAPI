package com.example.evently.services.tag;

import com.example.evently.dto.event.res.EventRes;
import com.example.evently.dto.tag.PostMultTagsReq;
import com.example.evently.dto.tag.TagReq;
import com.example.evently.models.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAll();

    Tag getById(Long id);

    Tag create(TagReq req);
    EventRes addEventTags(Long eventId, PostMultTagsReq req);

    EventRes delete(Long id, TagReq req);
}
