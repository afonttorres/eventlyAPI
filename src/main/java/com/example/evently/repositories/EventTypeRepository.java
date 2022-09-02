package com.example.evently.repositories;

import com.example.evently.models.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}
