package com.example.evently.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Role {

    public enum RoleName{
        ROLE_USER,
        ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleName name;


    public Role(){

    }

    public Role(Integer id, RoleName name) {
        this.id = id;
        this.name = name;
    }

    public Role(RoleName name){
        this.name = name;
    }
}
