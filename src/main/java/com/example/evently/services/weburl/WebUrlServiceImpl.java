package com.example.evently.services.weburl;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.output.Message;
import com.example.evently.dto.weburl.WebUrlReq;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.models.Type;
import com.example.evently.models.WebUrl;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.event.OnlineEvent;
import com.example.evently.models.user.User;
import com.example.evently.repositories.WebUrlRepository;
import com.example.evently.services.event.online.OnlineEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebUrlServiceImpl implements WebUrlService{


    WebUrlRepository webUrlRepository;
    OnlineEventService onlineEventService;
    AuthFacade authFacade;

    @Autowired
    public WebUrlServiceImpl(WebUrlRepository webUrlRepository, OnlineEventService onlineEventService, AuthFacade authFacade) {
        this.webUrlRepository = webUrlRepository;
        this.onlineEventService = onlineEventService;
        this.authFacade = authFacade;
    }

    private User getAuth(){
        Optional<User> auth = authFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundEx("User Not Found", "U-404");
        return auth.get();
    }


    @Override
    public List<WebUrl> getAll() {
        return webUrlRepository.findAll();
    }


    @Override
    public WebUrl getById(Long id) {
        var url = webUrlRepository.findById(id);
        if(url.isEmpty())
            throw  new NotFoundEx("Web url not found", "WU-404");
        return url.get();
    }

    @Override
    public WebUrl getByEventId(Long id) {
        var event = onlineEventService.getById(id);
        var url = webUrlRepository.findByEventId(id).stream().findAny();
        if(url.isEmpty())
            throw  new NotFoundEx("Web url not found", "WU-404");
        return url.get();
    }

    @Override
    public Message create(Long id, WebUrlReq req) {
        var event = onlineEventService.getById(id);
        if(event.getPublisher() != this.getAuth() && !authFacade.isAdmin())
            throw new BadReqEx("Only event publisher add a web url!", "WU-001");
        if(!event.getType().equals(Type.ONLINE))
            throw new BadReqEx("Only online events can have a web url", "WU-002");
        var url = new WebUrl(req.getUrl(), event);
        this.resetWebUrl(event);
        webUrlRepository.save(url);
        onlineEventService.addLocationToEvent(url, event);
        return new Message("Web url "+url.getUrl()+" added to event "+event.getTitle()+" !");
    }

    @Override
    public Message delete(Long id) {
        var event = onlineEventService.getById(id);
        this.resetWebUrl(event);
        onlineEventService.addLocationToEvent(null, event);
        return new Message("Web url deleted!");
    }


    private void resetWebUrl(OnlineEvent event){
        var eventDirections = webUrlRepository.findByEventId(event.getId());
        webUrlRepository.deleteAll(eventDirections);
    }
}
