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
//    @Column(name = "DoctorID", nullable = true)
//    private Integer doctorId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Integer getDoctorId() {
//        return doctorId;
//    }
//
//    public void setDoctorId(Integer doctorId) {
//        this.doctorId = doctorId;
//    }

    public Integer getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Integer technicianId) {
        this.technicianId = technicianId;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public TestType getType() {
        return type;
    }

    public void setType(TestType type) {
        this.type = type;
    }
}

