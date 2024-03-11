package com.example.springboot.Entities;

import jakarta.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Patient")
public class Patient {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
