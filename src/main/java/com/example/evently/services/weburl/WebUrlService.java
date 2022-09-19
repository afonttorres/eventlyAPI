package com.example.evently.services.weburl;

import com.example.evently.dto.output.Message;
import com.example.evently.dto.weburl.WebUrlReq;
import com.example.evently.models.WebUrl;

import java.util.List;

public interface WebUrlService {
    List<WebUrl> getAll();

    WebUrl getByEventId(Long id);

    WebUrl getById(Long id);

    Message create(Long id, WebUrlReq req);

    Message delete(Long id);
}
