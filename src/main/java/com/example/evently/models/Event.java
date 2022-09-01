package com.example.evently.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    @NotBlank
    @Size(min = 3, max = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private User publisher;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name="events", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name="category_id"))
//    @JsonIgnore
//    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @JsonSerialize
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Set<Participation> participants;


    public int participantsCount(){
        return this.participants.size();
    }

    public boolean isParticipant(User auth){
        var participant = participants.stream().filter(Save->Save.getParticipant() == auth).findFirst();
        if(participant.isEmpty()) {
            return false;
        }
        return true;
    }
}
