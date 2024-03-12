package com.example.springboot.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
//    @Column(name = "TypeID")
//    private Integer typeId;
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "Name")
    private String name;
    @Column(name = "Email")
    private String email;
    @Column(name = "NIC", nullable = true)
    private String nic;
    @Column(name = "Gender", nullable = true)
    private String gender;

    @ManyToOne
    @JoinColumn(name = "TypeID")
    private UserType type;
}
