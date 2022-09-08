package com.example.evently.models.event;

import com.example.evently.models.Type;
import com.example.evently.models.WebUrl;
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
public class OnlineEvent extends Event{

    @OneToOne(mappedBy = "event")
    @JoinColumn(name = "web_url_id")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    WebUrl webUrl;

    public OnlineEvent(){
        super.setType(Type.ONLINE);
    }
}
