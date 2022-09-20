package com.example.evently.fakers;

import com.example.evently.dto.event.req.EventJsonReq;
import com.example.evently.mappers.event.OfflineEventMapper;
import com.example.evently.mappers.event.OnlineEventMapper;
import com.example.evently.models.*;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.event.OnlineEvent;
import com.example.evently.models.user.User;
import com.example.evently.repositories.*;
import com.example.evently.repositories.event.EventRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataSeed {


    private RoleRepository roleRepository;
    private AuthRepository authRepository;
    private EventRepository eventRepository;
    private PasswordEncoder encoder;
    private TagRepository tagRepository;
    private RequirementRepository requirementRepository;
    private ParticipationRepository participationRepository;

    @Autowired
    public DataSeed(RoleRepository roleRepository,
                    AuthRepository authRepository,
                    EventRepository eventRepository,
                    PasswordEncoder encoder,
                    TagRepository tagRepository,
                    RequirementRepository requirementRepository,
                    ParticipationRepository participationRepository
    ) {
        this.roleRepository = roleRepository;
        this.authRepository = authRepository;
        this.eventRepository = eventRepository;
        this.encoder = encoder;
        this.tagRepository = tagRepository;
        this.requirementRepository = requirementRepository;
        this.participationRepository = participationRepository;
    }

    @PostConstruct
    public void addData(){
        this.createUsers();
        this.createEvents();
    }

    public void createUsers(){
        Set<Role> userRoles = Set.of(roleRepository.findByName(Role.RoleName.ROLE_USER).get());

        var agnes = new User();
        agnes.setRoles(userRoles);
        agnes.setUsername("afonttorres");
        agnes.setEmail("afonttorres@gmail.com");
        agnes.setPassword(encoder.encode("password1234"));
        agnes.setName("Agnes");
        agnes.setSurname("Font");

        var xevi = new User();
        xevi.setRoles(userRoles);
        xevi.setUsername("xcapde");
        xevi.setEmail("xcapde7@gmail.com");
        xevi.setPassword(encoder.encode("password1234"));
        xevi.setName("Xevi");
        xevi.setSurname("Capde");

        var laura = new User();
        laura.setRoles(userRoles);
        laura.setUsername("lauraparra");
        laura.setEmail("lp@gmail.com");
        laura.setPassword(encoder.encode("password1234"));
        laura.setName("Laura");
        laura.setSurname("Parra");

        var joel = new User();
        joel.setRoles(userRoles);
        joel.setUsername("joelblasi");
        joel.setEmail("jb@gmail.com");
        joel.setPassword(encoder.encode("password1234"));
        joel.setName("Joel");
        joel.setSurname("Blasi");

        authRepository.saveAll(List.of(agnes, xevi, laura, joel));
        System.out.println("Users saved!");
    }

    public void createTag(String tag){
        if(tagRepository.findByName(tag).isPresent()){
            return;
        }
        var newTag = new Tag(tag);
        tagRepository.save(newTag);
    }

    public void createTags(String[] tags){
        Arrays.stream(tags).forEach(t -> this.createTag(t));
    }

    public List<Tag> findTags(String[] tags){
        var req = Arrays.stream(tags).map(i -> i).collect(Collectors.toList());
        return tagRepository.findAll().stream()
                .filter(t-> req.contains(t.getName()))
                .collect(Collectors.toList());
    }
    public Event createEvent(EventJsonReq req){
        this.createTags(req.getTags());
        var tags = this.findTags(req.getTags());
        var user = authRepository.findByUsername(req.getUsername()).get();
       if(req.getType().equals("offline")){
           return this.createOffline(req, tags, user);
       }
       return this.createOnline(req, tags, user);
    }

    public OnlineEvent createOnline(EventJsonReq req, List<Tag> tags, User publisher){
        return new OnlineEventMapper().mapJsonReqToOnEvent(req, tags, publisher);
    }

    public OfflineEvent createOffline(EventJsonReq req, List<Tag> tags,  User publisher){
        return new OfflineEventMapper().mapJsonReqToOffEvent(req, tags, publisher);
    }

    public void createEvents(){
        List<Event> events = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<EventJsonReq>> typeReference = new TypeReference<List<EventJsonReq>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/events.json");
        try{
            List<EventJsonReq> eventsReq = mapper.readValue(inputStream, typeReference);
            eventsReq.forEach(req -> events.add(this.createEvent(req)));
            eventRepository.saveAll(events);
            System.out.println("Events saved!");
        }catch (IOException e){
            System.out.println("Unable to save events: "+ e.getMessage());
        }
    }

}
