package com.example.evently.repositories;

import com.example.evently.models.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
    @Query("select d from Direction d where d.event.id = :id")
    List<Direction> findByEventId(@Param("id") Long id);
}
