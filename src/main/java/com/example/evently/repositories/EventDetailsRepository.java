package com.example.evently.repositories;

import com.example.evently.models.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, Long> {
}
