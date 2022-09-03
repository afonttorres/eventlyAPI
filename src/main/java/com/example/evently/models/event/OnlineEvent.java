package com.example.evently.models.event;

import com.example.evently.models.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class OnlineEvent extends Event{
    String webUrl;
    public OnlineEvent(){
        super.setType(Type.ONLINE);
    }
}
