package com.example.evently.fakers;

import com.example.evently.dto.event.req.EventJsonReq;
import com.example.evently.models.*;
import com.example.evently.repositories.*;
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
    private ParticipationRepository participationRepository;
    private EventTypeRepository typeRepository;

    @Autowired
    public DataSeed(RoleRepository roleRepository,
                    AuthRepository authRepository,
                    EventRepository eventRepository,
                    PasswordEncoder encoder,
                    TagRepository tagRepository,
                    ParticipationRepository participationRepository,
                    EventTypeRepository typeRepository
    ) {
        this.roleRepository = roleRepository;
        this.authRepository = authRepository;
        this.eventRepository = eventRepository;
        this.encoder = encoder;
        this.tagRepository = tagRepository;
        this.participationRepository = participationRepository;
        this.typeRepository = typeRepository;
    }

    @PostConstruct
    public void addData(){
        new EventTypeInitializer(this.typeRepository).setEventTypes();
        this.createUsers();
        this.createEvents();
    }

    public void createUsers(){
        Set<Role> userRoles = Set.of(roleRepository.findByName(Role.RoleName.ROLE_USER).get());

        var agnes = new User();
        agnes.setRoles(userRoles);
        agnes.setUsername("afonttorres");
        agnes.setEmail("aft@gmail.com");
        agnes.setPassword(encoder.encode("password1234"));
        agnes.setName("Agnes");
        agnes.setSurname("Font");

        var xevi = new User();
        xevi.setRoles(userRoles);
        xevi.setUsername("xcapde");
        xevi.setEmail("xcapde@gmail.com");
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
        System.out.println(newTag);
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
        var event = new Event();
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setType(typeRepository.findById(req.getType()).get());
        event.setTags(findTags(req.getTags()));
        event.setPublisher(authRepository.findByUsername(req.getUsername()).get());
        return event;
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
