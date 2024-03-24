package com.example.springboot.DTOs;

import com.example.springboot.Entities.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class UserDto {
    public Integer id;
    public String username;
    public String password;

    public String name;
    public String email;
    public String nic;
    public String gender;
    public Integer type;
}
