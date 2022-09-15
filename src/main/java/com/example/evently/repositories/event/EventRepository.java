package com.example.evently.repositories.event;

import com.example.evently.models.Type;
import com.example.evently.models.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e inner join e.participants participants where participants.participant.id = :id")
    List<Event> findByParticipantId(@Param("id") Long id);

    @Query("select e from Event e where e.publisher.id = :id")
    List<Event> findByPublisherId(@Param("id") Long id);

    @Query("select e from Event e inner join e.tags tags where upper(tags.name) = upper(:name)")
    List<Event> findByTag(@Param("name") String name);

    @Query("select e from Event e where e.type = :type")
    List<Event> findByType(@Param("type") Type type);


}
