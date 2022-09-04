package com.example.evently.models.event;

import com.example.evently.models.Participation;
import com.example.evently.models.Requirement;
import com.example.evently.models.Tag;
import com.example.evently.models.Type;
import com.example.evently.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    private List<Requirement> requirements = new ArrayList<>();

//    @OneToOne
//    @JoinColumn(name = "event_type")
//    private EventType type;

    private Type type;
    String location;

    @OneToMany(mappedBy = "event")
    @JsonSerialize
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
