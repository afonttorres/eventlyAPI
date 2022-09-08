package com.example.evently.models;

import com.example.evently.models.event.OfflineEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Data
@Table(name = "directions")
@AllArgsConstructor
@NoArgsConstructor
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    String country = "pais";
    String province = "provincia";
    String city = "ciutat";
    String street ="carrer";
    String building = "numero";
    String door = "porta";


    @OneToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private OfflineEvent event;

    public String toString(){
        return this.country+", "+this.province+", "+this.city+", "+this.street+", "+this.building+", "+this.door;
    }
}
