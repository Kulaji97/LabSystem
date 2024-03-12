package com.example.springboot.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Test")
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
//    @Column(name = "AppointmentID")
//    private Integer appointmentId;
//    @Column(name = "TypeID")
//    private Integer typeId;
    @Column(name = "DoctorID", nullable = true)
    private Integer doctorId;
    @Column(name = "TechnicianID", nullable = true)
    private Integer technicianId;
    @Column(name = "ResultDescription", nullable = true)
    private String resultDescription;

    @ManyToOne
    @JoinColumn(name = "AppointmentID")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "TypeID")
    private TestType type;
}

