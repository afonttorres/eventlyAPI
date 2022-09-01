package com.example.evently.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;
    @NotBlank
    @Size(min=2, max=40)
    private String name;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name="categories", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name="event_id"))
//    @JsonIgnore
//    private Set<Event> events;


}
