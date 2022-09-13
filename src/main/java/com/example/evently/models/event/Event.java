package com.example.evently.models.event;

import com.example.evently.models.*;
import com.example.evently.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event {
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

    @OneToMany
    @JoinColumn(name = "tags")
    private List<Tag> tags = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "event_id")
    @JsonSerialize
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Requirement> requirements = new ArrayList<>();

    @JoinColumn(name = "type")
    private Type type;
    @JoinColumn(name="date")
    private Date date;
    @JoinColumn(name = "location")
    String location;

    @OneToMany(mappedBy = "event")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Image> images;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Participation> participants = new ArrayList<>();

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