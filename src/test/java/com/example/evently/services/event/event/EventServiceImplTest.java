package com.example.evently.services.event.event;

import com.example.evently.auth.facade.AuthFacade;
import com.example.evently.dto.event.req.EventReq;
import com.example.evently.dto.event.req.EventReqUpdate;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import com.example.evently.mappers.DateMapper;
import com.example.evently.mappers.event.EventMapper;
import com.example.evently.models.Role;
import com.example.evently.models.Tag;
import com.example.evently.models.Type;
import com.example.evently.models.event.Event;
import com.example.evently.models.event.OfflineEvent;
import com.example.evently.models.event.OnlineEvent;
import com.example.evently.models.user.User;
import com.example.evently.repositories.event.EventRepository;
import com.example.evently.repositories.event.OfflineRepository;
import com.example.evently.repositories.event.OnlineRepository;
import com.example.evently.services.email.EmailService;
import com.example.evently.services.event.offline.OfflineEventService;
import com.example.evently.services.event.online.OnlineEventService;
import com.example.evently.services.notification.NotificationService;
import com.example.evently.services.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class EventServiceImplTest {

    EventService eventService;
    List<Event> events;
    User auth;
    User notAuth;
    EventReq onReq;
    EventReqUpdate onReqUp;
    EventReq offReq;
    EventReqUpdate offReqUp;

    @Mock
    EventRepository eventRepository;
    @Mock
    OfflineEventService offlineEventService;
    @Mock
    OnlineEventService onlineEventService;
    @Mock
    AuthFacade authFacade;
    @Mock
    UserService userService;
    @Mock
    NotificationService notificationService;


    @BeforeEach
    void init(){
       eventService  = new EventServiceImpl(eventRepository, authFacade, onlineEventService, offlineEventService, userService, notificationService);
       auth = this.createUser();
       notAuth = this.notAuth();
       events = this.createMultEvents();
       onReq = this.createOnlineReq();
       onReqUp = this.createUpdateOnReq();
       offReq = this.createOfflineReq();
       offReqUp = this.createUpdateOffReq();
    }

    @Test
    void getCompleteEventByIdShouldReturnACompleteEventById() {
        Long id = 1L;
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(this.events.get(0)));
        var sut = eventService.getCompleteEventById(id).getTitle();
        assertThat(sut, equalTo("title1"));
    }

    @Test
    void getCompleteEventByIdShouldReturnNotFoundEx() {
        Long id = 1L;
        Exception ex = assertThrows(NotFoundEx.class, ()->{
          eventService.getCompleteEventById(id);
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("Event Not Found"));
    }


    @Test
    void getAllShouldReturnAllEventsWhenUserNotLogged() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.empty());
        Mockito.when(eventRepository.findAllByOrderByIdDesc()).thenReturn(events);
        var sut = eventService.getAll().size();
        assertThat(sut, equalTo(12));
    }

    @Test
    void getAllShouldReturnAllEventsWhenUserLogged() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(auth));
        Mockito.when(eventRepository.findAllByOrderByIdDesc()).thenReturn(events);
        var sut = eventService.getAll().size();
        assertThat(sut, equalTo(12));
    }

    @Test
    void getEventByIdShouldReturnEventByPamId() {
        Long id = 1L;
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(this.events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        var sut = eventService.getEventById(id);
        var res = new EventMapper().mapEventToResAuth(this.events.get(0), this.auth);
        assertThat(sut, equalTo(res));
    }

    @Test
    void getEventByIdShouldThrowNotFoundExWhenEventNotFound() {
        Long id = 1L;
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.getEventById(id);
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("Event Not Found"));
    }

    @Test
    void createShouldCreateOffEvent() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.save(any(Event.class)))
                .thenReturn(events.get(1));
        Mockito.when(offlineEventService.create(any(EventReq.class), any(User.class)))
                .thenReturn(events.get(1));
        var sut = eventService.create(offReq);
        System.out.println(sut);
        assertThat(sut, equalTo(new EventMapper().mapEventToRes(events.get(1))));
    }

    @Test
    void createShouldCreateOnEvent() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.save(any(Event.class)))
                .thenReturn(events.get(0));
        Mockito.when(onlineEventService.create(any(EventReq.class), any(User.class)))
                .thenReturn(events.get(0));
        var sut = eventService.create(onReq);
        System.out.println(sut);
        assertThat(sut, equalTo(new EventMapper().mapEventToRes(events.get(0))));
    }

    @Test
    void createShouldThrowNotFoundExWhenNotAuth() {
        Long id = 1L;
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.create(offReq);
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("User Not Found"));
    }

    @Test
    void createShouldThrowBadReqExWhenTypeIsEmpty() {
        Long id = 1L;
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Exception ex = assertThrows(BadReqEx.class, ()->{
            offReq.setType("");
            eventService.create(offReq);
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("Type can't be empty!"));
    }

    @Test
    void createShouldThrowBadReqExWhenTypeIsDiffFromOnlineAndOffline() {
        Long id = 1L;
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Exception ex = assertThrows(BadReqEx.class, ()->{
            offReq.setType("test");
            eventService.create(offReq);
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("Wrong type!"));
    }

    @Test
    void deleteShouldDeleteAnEvent() {
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        var sut = eventService.delete(1L);
        var res = new EventMapper().mapEventToRes(events.get(0));
        assertThat(sut, equalTo(res));
    }

    @Test
    void deleteShouldThrowNotFoundExWhenEventNotFound() {
        Long id = 1L;
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.delete(id);
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("Event Not Found"));
    }

    @Test
    void deleteShouldThrowNotFoundExWhenUserNotFound() {
        Long id = 1L;
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.delete(id);
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("User Not Found"));
    }

    @Test
    void deleteShouldThrowBadReqExWhenUserNotPublisher() {
        Long id = 1L;
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(notAuth));
        Exception ex = assertThrows(BadReqEx.class, ()->{
            eventService.delete(id);
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("Only event publisher is allowed to delete it!"));
    }

    @Test
    void updateShouldUpdateEventWhenTypeDidNotChange() {
        Long id = 1L;
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        var sut = eventService.update(id, onReqUp);
        assertThat(sut, equalTo(new EventMapper().mapEventToRes(events.get(0))));
    }

    @Test
    void updateShouldUpdateEventWhenTypeGoesFromOnToOff() {
        Long id = 1L;
        var event = new OfflineEvent();
        event.setTitle("title1");
        event.setDescription("desc1");
        event.setDate(new Date());
        event.setPublisher(this.auth);
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(offlineEventService.createFromOnlineEvent(offReqUp, events.get(0)))
                .thenReturn(event);
        var sut = eventService.update(id, offReqUp);
        assertThat(sut, equalTo(new EventMapper().mapEventToRes(event)));
    }

    @Test
    void updateShouldUpdateEventWhenTypeGoesFromOffToOn() {
        Long id = 2L;
        var event = new OnlineEvent();
        event.setTitle("title2");
        event.setDescription("desc2");
        event.setDate(new Date());
        event.setPublisher(this.auth);
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(1)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(onlineEventService.createFromOfflineEvent(onReqUp, events.get(1)))
                .thenReturn(event);
        var sut = eventService.update(id, onReqUp);
        assertThat(sut, equalTo(new EventMapper().mapEventToRes(event)));
    }

    @Test
    void updateShouldReturnNotFoundExWhenEventNotFound() {
        Long id = 1L;
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.update(id, onReqUp);
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("Event Not Found"));
    }

    @Test
    void updateShouldReturnNotFoundExWhenUserNotAuth() {
        Long id = 1L;
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.update(id, onReqUp);
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("User Not Found"));
    }

    @Test
    void updateShouldReturnNotFoundExWhenNotPublisher() {
        Long id = 1L;
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.notAuth));
        Exception ex = assertThrows(BadReqEx.class, ()->{
            eventService.update(id, onReqUp);
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("Only event publisher is allowed to update it!"));
    }

    @Test
    void setEventTagsShouldReturnEventWithTags() {
        Long id = 1L;
        List<Tag> tags = List.of(new Tag(1L, "test"), new Tag(2L, "test2"), new Tag(3L, "test3"));
        var res = events.get(0);
        res.setTags(tags);
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(res);
        var sut = eventService.setEventTags(id, tags);
        assertThat(sut, equalTo(res));
    }

    @Test
    void setEventTagsShouldThrowNotFoundExWhenEventNotFound() {
        Long id = 1L;
        List<Tag> tags = List.of(new Tag(1L, "test"), new Tag(2L, "test2"), new Tag(3L, "test3"));
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.setEventTags(id, tags);
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("Event Not Found"));
    }

    @Test
    void setEventTagsShouldThrowNotFoundExWhenUserNotFound() {
        Long id = 1L;
        List<Tag> tags = List.of(new Tag(1L, "test"), new Tag(2L, "test2"), new Tag(3L, "test3"));
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.setEventTags(id, tags);
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("User Not Found"));
    }
    @Test
    void setEventTagsShouldThrowNotFoundExWhenUserNotPublisher() {
        Long id = 1L;
        List<Tag> tags = List.of(new Tag(1L, "test"), new Tag(2L, "test2"), new Tag(3L, "test3"));
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.notAuth));
        Exception ex = assertThrows(BadReqEx.class, ()->{
            eventService.setEventTags(id, tags);
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("Only event publisher can add tags!"));
    }

    @Test
    void deleteEventTagShouldReturnEventWithoutTag() {
        Long id = 1L;
        List<Tag> tags = List.of(new Tag(1L, "test"), new Tag(2L, "test2"), new Tag(3L, "test3"));
        var event = events.get(0);
        event.setTags(tags);
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(event));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.save(any(Event.class))).thenReturn(event);
        var sut = eventService.deleteEventTag(id, tags.get(0));
        assertThat(sut, equalTo(event));
    }

    @Test
    void deleteEventTagsShouldThrowNotFoundExWhenEventNotFound() {
        Long id = 1L;
        List<Tag> tags = List.of(new Tag(1L, "test"), new Tag(2L, "test2"), new Tag(3L, "test3"));
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.deleteEventTag(id, tags.get(0));
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("Event Not Found"));
    }

    @Test
    void deleteEventTagsShouldThrowNotFoundExWhenUserNotFound() {
        Long id = 1L;
        List<Tag> tags = List.of(new Tag(1L, "test"), new Tag(2L, "test2"), new Tag(3L, "test3"));
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.deleteEventTag(id, tags.get(0));
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("User Not Found"));
    }
    @Test
    void deleteEventTagShouldThrowNotFoundExWhenUserNotPublisher() {
        Long id = 1L;
        List<Tag> tags = List.of(new Tag(1L, "test"), new Tag(2L, "test2"), new Tag(3L, "test3"));
        Mockito.when(eventRepository.findById(any(Long.class))).thenReturn(Optional.of(events.get(0)));
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.notAuth));
        Exception ex = assertThrows(BadReqEx.class, ()->{
            eventService.deleteEventTag(id, tags.get(0));
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("Only event publisher can add tags!"));
    }

    @Test
    void getUserJoinedEventsReturnsUserJoinedEventsAsEvents() {
        Mockito.when(eventRepository.findByParticipantId(any(Long.class))).thenReturn(events);
        var sut = eventService.getUserJoinedEvents(this.auth).size();
        assertThat(sut, equalTo(12));
    }

    @Test
    void getUserJoinedEventsReturnsUserJoinedEventsAsRes() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.findByParticipantId(any(Long.class))).thenReturn(events);
        var sut = eventService.getUserJoinedEvents().size();
        assertThat(sut, equalTo(12));
    }

    @Test
    void getUserJoinedEventsShouldThrowNotFoundExWhenUserNotAuth() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.empty());
        Mockito.when(eventRepository.findByParticipantId(any(Long.class))).thenReturn(events);
        Exception ex = assertThrows(NotFoundEx.class, () -> {
            eventService.getUserJoinedEvents();
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("User Not Found"));
    }

        @Test
    void getAuthPublishedEventsShouldReturnAuthPublishedEvents() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.findByPublisherId(any(Long.class))).thenReturn(this.events);
        var sut = eventService.getAuthPublishedEvents().size();
        assertThat(sut, equalTo(12));
    }

    @Test
    void getAuthPublishedEventsShouldThrowNotFoundEx() {
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.getAuthPublishedEvents();
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("User Not Found"));
    }

    @Test
    void getUserPublisherEventsShouldReturnAListOfEventResByPamId(){
        Long id = 1L;
        Mockito.when(userService.getById(any(Long.class))).thenReturn(this.notAuth);
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.findByPublisherId(any(Long.class))).thenReturn(events);
        var sut = eventService.getUserPublishedEvents(id).size();
        assertThat(sut, equalTo(12));
    }

    @Test
    void getUserPublisherEventsShouldThrowNotFoundExWhenCantFindUser() {
        Mockito.when(userService.getById(any(Long.class))).thenReturn(null);
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.getAuthPublishedEvents();
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("User Not Found"));
    }

    @Test
    void getUserPublisherEventsShouldThrowNotFoundExWhenNotAuth() {
        Mockito.when(userService.getById(any(Long.class))).thenReturn(this.notAuth);
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundEx.class, ()->{
            eventService.getAuthPublishedEvents();
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("User Not Found"));
    }

    @Test
    void getByTagShouldReturnEventResListByTagIfAuth() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.findByTag(any(String.class))).thenReturn(events);
        var sut = eventService.getByTag("tag").size();
        assertThat(sut, equalTo(12));
    }

    @Test
    void getByTagShouldReturnEventResListByTagIfNotAuth() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.empty());
        Mockito.when(eventRepository.findByTag(any(String.class))).thenReturn(events);
        var sut = eventService.getByTag("tag").size();
        assertThat(sut, equalTo(12));
    }

    @Test
    void getByTagShouldThrowBadReqExIfTagIsEmptyString() {
        Exception ex = assertThrows(BadReqEx.class, ()->{
            eventService.getByTag("");
        });
        var sut = ex.getMessage();
        assertTrue(sut.equals("Tag can't be empty!"));
    }

    @Test
    void getByTypeShouldReturnEventResByTypeIfAuthTypeOff() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.findByType(any(Type.class))).thenReturn(events);
        var sut = eventService.getByType("offline");
        assertThat(sut, equalTo(new EventMapper().mapMultipleEventsToResAuth(events, this.auth)));
    }

    @Test
    void getByTypeShouldReturnEventResByTypeIfAuthTypeOn() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.of(this.auth));
        Mockito.when(eventRepository.findByType(any(Type.class))).thenReturn(events);
        var sut = eventService.getByType("online");
        assertThat(sut, equalTo(new EventMapper().mapMultipleEventsToResAuth(events, this.auth)));
    }

    @Test
    void getByTypeShouldReturnEventResByTypeIfNotAuth() {
        Mockito.when(authFacade.getAuthUser()).thenReturn(Optional.empty());
        Mockito.when(eventRepository.findByType(any(Type.class))).thenReturn(events);
        var sut = eventService.getByType("offline");
        assertThat(sut, equalTo(new EventMapper().mapMultipleEventsToRes(events)));
    }

    @Test
    void getByTypeShouldThrowBadReqExWhenStringInvalid() {
        Exception ex = assertThrows(BadReqEx.class, ()->{
            eventService.getByType("");
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("Type can't be empty!"));
    }

    @Test
    void getByTypeShouldThrowBadReqExWhenNotOfflineOrOnline() {
        Exception ex = assertThrows(BadReqEx.class, ()->{
            eventService.getByType("test");
        });
        var sut = ex.getMessage();
        System.out.println(sut);
        assertTrue(sut.equals("Wrong type!"));
    }

    public OfflineEvent createOffEvent(Long id, String title, String desc){
        var event = new OfflineEvent();
        event.setId(id);
        event.setTitle(title);
        event.setDescription(desc);
        event.setDate(new Date());
        event.setPublisher(this.auth);
        return event;
    }

    public OnlineEvent createOnEvent(Long id, String title, String desc){
        var event = new OnlineEvent();
        event.setId(id);
        event.setTitle(title);
        event.setDescription(desc);
        event.setDate(new Date());
        event.setPublisher(this.auth);
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

    public User createUser(){
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

    public User notAuth(){
        var user = new User();
        user.setId(1L);
        user.setRoles(Set.of(new Role(2, Role.RoleName.ROLE_USER)));
        user.setUsername("notauth");
        user.setEmail("notauth@gmail.com");
        user.setPassword("password1234");
        user.setName("Not");
        user.setSurname("Auth");
        return user;
    }


    public EventReq createOnlineReq(){
        return new EventReq("title", "desc", "online", new DateMapper().convertDateToLocalDate(new Date(22,9,15)));
    }

    public EventReq createOfflineReq(){
        return new EventReq("title", "desc", "offline", new DateMapper().convertDateToLocalDate(new Date(22,9,15)));
    }

    public EventReqUpdate createUpdateOffReq(){
        return new EventReqUpdate("title1", "desc1", "offline", new DateMapper().convertDateToLocalDate(new Date(22,9,15)));
    }

    public EventReqUpdate createUpdateOnReq(){
        return new EventReqUpdate("title1", "desc1", "online", new DateMapper().convertDateToLocalDate(new Date(22,9,15)));
    }
}