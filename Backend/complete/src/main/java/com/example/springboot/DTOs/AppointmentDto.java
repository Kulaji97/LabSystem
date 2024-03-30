package com.example.springboot.DTOs;

import com.example.springboot.Entities.TestType;
import com.example.springboot.Entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AppointmentDto {

    public Integer id;
    public Integer doctorId;
    public Integer number;
    public String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    public LocalDateTime time;
    public BigDecimal amount;
    public String paymentStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    public LocalDateTime paymentDate;
    public String paymentMethod;
    public Integer patientId;
    public Integer testTypeId;
    public String testName;
}
