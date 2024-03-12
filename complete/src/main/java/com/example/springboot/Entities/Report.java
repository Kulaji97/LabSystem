package com.example.springboot.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Report")
public class Report {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
//    @Column(name = "TestID")
//    private Integer testId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Path", nullable = true)
    private String path;

    @ManyToOne
    @JoinColumn(name = "TestID")
    private Test test;
}

