package com.example.evently.repositories.event;

import com.example.evently.models.Type;
import com.example.evently.models.event.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e inner join e.participants participants " +
            "where participants.participant.id = :id " +
            "order by e.id DESC")
    List<Event> findByParticipantId(@Param("id") Long id);

    @Query("select e from Event e where e.publisher.id = :id order by e.id DESC")
    List<Event> findByPublisherId(@Param("id") Long id);

    @Query("select e from Event e inner join e.tags tags where upper(tags.name) = upper(:name) order by e.id DESC")
    List<Event> findByTag(@Param("name") String name);

    @Query("select e from Event e where e.type = :type order by e.id DESC")
    List<Event> findByType(@Param("type") Type type);

    @Query("select e from Event e order by e.id DESC")
    List<Event> findAllByOrderByIdDesc();



}
