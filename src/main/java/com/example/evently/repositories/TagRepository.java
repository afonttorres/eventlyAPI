package com.example.evently.repositories;

import com.example.evently.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select t from Tag t where upper(t.name) = upper(:name)")
    List<Tag> findAllByName(@Param("name") String name);

    boolean existsByName(String name);
    Optional<Tag> findByName(String name);
}
