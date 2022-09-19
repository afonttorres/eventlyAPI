package com.example.evently.models.event;

import com.example.evently.mappers.event.EventMapper;
import com.example.evently.mappers.event.OnlineEventMapper;
import com.example.evently.models.Role;
import com.example.evently.models.Tag;
import com.example.evently.models.Type;
import com.example.evently.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventTest {

    List<Event> events;
    User admin;

    @BeforeEach
    void init(){
        events = this.createMultEvents();
        admin = this.createAdmin();
    }

    @Test
    void OfflineEventShouldBeConvertedToString() {
        var event = events.get(1);
        var sut = event.toString();
        assertThat(sut, equalTo("Offline Event [title: title6, desc: desc6, type: offline , date: "+new Date().toString()+", loc:  , tags: []]"));
    }

    @Test
    void OnlineEventShouldBeConvertedToString() {
        var event = events.get(0);
        var sut = event.toString();
        assertThat(sut, equalTo("Online Event [title: title1, desc: desc1, type: online , date: "+new Date().toString()+", loc:  , tags: []]"));
    }

    @Test
    void OnlineEventShouldBeConvertedToStringWithTags() {
        var event = events.get(0);
        event.setTags(List.of(new Tag(1L, "esport"), new Tag(2L, "health"), new Tag(3L, "oci")));
        var sut = event.toString();
        assertThat(sut, equalTo("Online Event [title: title1, desc: desc1, type: online , date: "+new Date().toString()+", loc:  , tags: [Tag(id=1, name=esport), Tag(id=2, name=health), Tag(id=3, name=oci)]]"));
    }

    public OfflineEvent createOffEvent(Long id, String title, String desc){
        var event = new OfflineEvent();
        event.setId(id);
        event.setTitle(title);
        event.setDescription(desc);
        event.setDate(new Date());
        event.setPublisher(this.admin);
        return event;
    }

    public OnlineEvent createOnEvent(Long id, String title, String desc){
        var event = new OnlineEvent();
        event.setId(id);
        event.setTitle(title);
        event.setDescription(desc);
        event.setDate(new Date());
        event.setPublisher(this.admin);
        return event;
    }

    public List<Event> createMultEvents(){
        List<Event> res = new ArrayList<>();
        for (var i = 0; i <= 5; i++){
            res.add(this.createOnEvent(Long.valueOf((i+1)),"title"+(i+1), "desc"+(i+1)));
            res.add(this.createOffEvent(Long.valueOf((i+1+5)), "title"+(i+1+5), "desc"+(i+1+5)));
        }
        return res;
    }

    public User createAdmin(){
        var user = new User();
        user.setId(1L);
        user.setRoles(Set.of(new Role(1, Role.RoleName.ROLE_ADMIN), new Role(2, Role.RoleName.ROLE_USER)));
        user.setUsername("auth");
        user.setEmail("auth@gmail.com");
        user.setPassword("password1234");
        user.setName("Auth");
        user.setSurname("User");
        return user;
    }
}