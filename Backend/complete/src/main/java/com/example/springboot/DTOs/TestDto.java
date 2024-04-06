package com.example.springboot.DTOs;

import com.example.springboot.Entities.Appointment;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class TestDto {
    public Integer id;
    public Integer technicianId;
    public String technicianName;
    public String resultDescription;
    public String appointmentId;
    public String path;
    public String filename;
}
