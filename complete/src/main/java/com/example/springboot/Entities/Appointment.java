package com.example.springboot.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
//    @Column(name = "PatientID")
//    private Integer patientId;
    @Column(name = "Number")
    private Integer number;
    @Column(name = "Time")
    private LocalDateTime time;
    @Column(name = "Amount")
    private BigDecimal amount;
    @Column(name = "PaymentStatus", nullable = true)
    private Integer paymentStatus;
    @Column(name = "PaymentDate" , nullable = true)
    private LocalDateTime paymentDate;
    @Column(name = "PaymentMethod", nullable = true)
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "PatientID")
    private User patient;
}

