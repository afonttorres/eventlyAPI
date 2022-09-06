package com.example.evently.repositories.event;

import com.example.evently.models.event.OfflineEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfflineRepository extends JpaRepository<OfflineEvent, Long> {
}
