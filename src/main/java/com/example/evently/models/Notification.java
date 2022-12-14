package com.example.evently.models;

import com.example.evently.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String subject;

    @Size(min=3, max=2500, message = "Notification description should have 3 to 2500 characters!")
    private String description;

    @Version
    private java.sql.Timestamp createdAt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "notified_id")
    private User notified;
    private boolean checked = false;

    public Notification(String subject, String description, User notified){
        this.subject = subject;
        this.description = description;
        this.notified = notified;
    }
}
