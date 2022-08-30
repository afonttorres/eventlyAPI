package com.example.evently.services.eventDetail;

import com.example.evently.models.Event;
import com.example.evently.models.EventDetails;
import com.example.evently.repositories.EventDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventDetailServiceImpl implements EventDetailService{
    EventDetailsRepository eventDetailsRepository;

    @Autowired
    public EventDetailServiceImpl(EventDetailsRepository eventDetailsRepository) {
        this.eventDetailsRepository = eventDetailsRepository;
    }

    private List<EventDetails> getAllEventDetails(){
        return this.eventDetailsRepository.findAll();
    }

//    private EventDetails create(EventDetailReq eventDetailReq){
//        return this.eventDetailsRepository.save();
//    }

}
