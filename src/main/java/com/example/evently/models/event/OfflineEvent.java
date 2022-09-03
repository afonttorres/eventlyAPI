package com.example.evently.models.event;

import com.example.evently.models.Direction;
import com.example.evently.models.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class OfflineEvent extends Event{
    @OneToOne
    @JoinColumn(name = "direction_id")
    Direction direction;

    public OfflineEvent(){
        super.setType(Type.OFFLINE);
    }
}
