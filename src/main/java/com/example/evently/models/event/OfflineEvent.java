package com.example.evently.models.event;

import com.example.evently.models.Direction;
import com.example.evently.models.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class OfflineEvent extends Event{
    @OneToOne
    @JoinColumn(name = "direction_id")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    Direction direction;

    public OfflineEvent(){
        super.setType(Type.OFFLINE);
    }
}
