package com.example.evently.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "direction")
@AllArgsConstructor
@NoArgsConstructor
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    String country;
    String province;
    String city;
    String street;
    String building;
    String door;

    public String toString(){
        return this.country+", "+this.province+", "+this.city+", "+this.street+", "+this.building+", "+this.door;
    }
}
