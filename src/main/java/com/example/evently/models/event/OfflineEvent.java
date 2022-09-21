package com.example.evently.models.event;

import com.example.evently.models.Direction;
import com.example.evently.models.Type;
import com.example.evently.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class OfflineEvent extends Event{
    @OneToOne(mappedBy = "event")
    @JoinColumn(name = "direction_id")
    @Cascade(CascadeType.DELETE)
    @JsonIgnore
    Direction direction;

    public OfflineEvent(String title, String description, User publisher, Date date){
        super(title, description, publisher, date);
        super.setType(Type.OFFLINE);
    }

    public OfflineEvent(){
        super.setType(Type.OFFLINE);
    }

    @Override
    public String toString() {
        return "Offline Event [title: "+getTitle().toLowerCase()+", desc: "+getDescription().toLowerCase()+", type: offline , date: "+getDate().toString().toLowerCase()+", loc: "+getLocation().toLowerCase()+" , tags: "+getTags().toString().toLowerCase()+"]";
    }

    @Override
    public String beautified(){
        return "offline event "+getTitle()+" planned in"+getLocation()+" the "+getDate().toGMTString().substring(0,getDate().toString().lastIndexOf(":"));
    }
}
