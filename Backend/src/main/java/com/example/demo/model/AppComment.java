package com.example.demo.model;

import javax.persistence.*;

@Entity(name = "AppComment")
@Table(name = "AppComment")
public class AppComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comment")
    private String comment;

    public AppComment(String comment) {
        this.comment = comment;
    }

    public AppComment() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
