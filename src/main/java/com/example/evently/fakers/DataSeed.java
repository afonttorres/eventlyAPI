package com.example.evently.fakers;

import com.example.evently.dto.events.req.EventJsonReq;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataSeed {


    private RoleRepository roleRepository;
    private AuthRepository authRepository;
    private EventRepository eventRepository;
    private PasswordEncoder encoder;
    private CategoryRepository categoryRepository;
    private ParticipationRepository participationRepository;

    @Autowired
    public DataSeed(RoleRepository roleRepository, AuthRepository authRepository, EventRepository eventRepository, PasswordEncoder encoder, CategoryRepository categoryRepository, ParticipationRepository participationRepository) {
        this.roleRepository = roleRepository;
        this.authRepository = authRepository;
        this.eventRepository = eventRepository;
        this.encoder = encoder;
        this.categoryRepository = categoryRepository;
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

    public Set<Category> findCategories(String[] cats){
        var categories = Set.of(cats);
        return categoryRepository.findAll().stream().filter(c-> categories.contains(c.getName())).collect(Collectors.toSet());
    }
    public void createCategories(String[] cats){
        var categories = Set.of(cats);
        categories.stream().forEach(c -> this.createCategory(c));
    }

    public void createCategory(String name){
        if(!categoryRepository.existsByName(name)){
            var cat = new Category();
            cat.setName(name);
            categoryRepository.save(cat);
        }
    }

    public void createParts(){
        eventRepository.findAll().forEach(e-> this.createPart(e));
    }

    public void createPart(Event event){
        authRepository.findAll().forEach(u -> {
            var part = new Participation();
            part.setParticipant(u);
            part.setEvent(event);
            participationRepository.save(part);
        });
        System.out.println("Participations saved!");
    }

    public Event createEvent(String title, String desc, String username, String[] categories, String[] participants){
        this.createCategories(categories);
        var event = new Event();
        event.setTitle(title);
        event.setDescription(desc);
        event.setCategories(this.findCategories(categories));
        event.setPublisher(authRepository.findByUsername(username).get());
        return event;
    }


    public void createEvents(){
        List<Event> events = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<EventJsonReq>> typeReference = new TypeReference<List<EventJsonReq>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/events.json");
        try{
            List<EventJsonReq> eventsReq = mapper.readValue(inputStream, typeReference);
            eventsReq.forEach(req -> events.add(this.createEvent(req.getTitle(), req.getDescription(), req.getUsername(), req.getCategories(), req.getParticipants())));
            eventRepository.saveAll(events);
            System.out.println("Events saved!");
//            this.createParts();
        }catch (IOException e){
            System.out.println("Unable to save events: "+ e.getMessage());
        }
    }

}
