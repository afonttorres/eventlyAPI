package com.example.evently.services.tag;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.tag.PostMultTagsReq;
import com.example.evently.dto.tag.TagReq;
import com.example.evently.models.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAll();

    Tag getById(Long id);

    Tag create(TagReq req);
    Message addEventTags(Long eventId, PostMultTagsReq req);

    Message delete(Long id, TagReq req);
}
