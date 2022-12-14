package com.example.evently.repositories;

import com.example.evently.models.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Long> {
    Optional<Requirement> findByName(String requirement);
    boolean existsByName(String requirement);

    @Query("select r from Requirement r where r.event.id = :id")
    List<Requirement> findByEventId(@Param("id") Long id);

    @Query("select distinct r from Requirement r where r.event.id = :id and upper(r.name) like upper(:name)")
    List<Requirement> findByNameInEvent(@Param("id") @NonNull Long id, @Param("name") @NonNull String name);
}
