package com.example.codefellowship.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String body;

    @CreationTimestamp
    private Date createdAt;


    @ManyToOne
    @JoinColumn( name = "idUser")
   ApplicationUser user;

    public Post(){}

    public Post(String body, Date createdAt ,ApplicationUser user){
    this.body=body;
    this.createdAt=createdAt;
    this.user=user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}
