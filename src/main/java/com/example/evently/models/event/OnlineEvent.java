package com.example.evently.models.event;

import com.example.evently.models.Type;
import com.example.evently.models.WebUrl;
import com.example.evently.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class OnlineEvent extends Event{

    @OneToOne(mappedBy = "event")
    @JoinColumn(name = "web_url_id")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    WebUrl webUrl;

    public OnlineEvent(String title, String description, User publisher, Date date){
        super(title, description, publisher, date);
        super.setType(Type.ONLINE);
    }

    public OnlineEvent(){
        super.setType(Type.ONLINE);
    }

    @Override
    public String toString() {
        return "Online Event [title: "+getTitle().toLowerCase()+", desc: "+getDescription().toLowerCase()+", type: online , date: "+getDate().toString().toLowerCase()+", loc: "+getLocation().toLowerCase()+" , tags: "+getTags().toString().toLowerCase()+"]";
    }

    @Override
    public String beautified(){
        return "online event "+getTitle()+" planned in"+getLocation()+" the "+getDate().toGMTString().substring(0,getDate().toString().lastIndexOf(":"));
    }
}
