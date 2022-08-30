package com.example.evently.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String surname;
    private String username;
    private String email;
    @JsonIgnore
    private String password;

    @ManyToMany
    private Set<Role> roles;

    public User(String name, String surname, String username, String email, String encode){
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = encode;
    }

    public String getCompleteName(){
        return this.name+" "+this.surname;
    }

}
