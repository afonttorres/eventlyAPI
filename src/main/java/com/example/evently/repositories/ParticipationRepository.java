package com.example.evently.repositories;

import com.example.evently.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    @Query("select p from Participation p where upper(p.event.id) = upper(:id)")
    List<Participation> findByEventId(@Param("id") Long id);

    @Query("select p from Participation p where p.participant.id = :id")
    List<Participation> findByParticipantId(@Param("id") Long id);




}
