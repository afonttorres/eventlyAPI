package com.example.evently.repositories;

import com.example.evently.models.WebUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WebUrlRepository extends JpaRepository<WebUrl, Long> {
    @Query("select w from WebUrl w where upper(w.event.id) = upper(:id)")
    List<WebUrl> findByEventId(@Param("id") Long id);

}
