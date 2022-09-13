package com.example.evently.repositories.event;

import com.example.evently.models.event.OnlineEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineRepository extends JpaRepository<OnlineEvent, Long> {
}
