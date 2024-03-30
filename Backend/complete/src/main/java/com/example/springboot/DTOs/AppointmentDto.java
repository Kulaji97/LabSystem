package com.example.springboot.DTOs;

import com.example.springboot.Entities.TestType;
import com.example.springboot.Entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AppointmentDto {

    public Integer id;
    public Integer doctorId;
    @Nullable
    public Integer number;
    public String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Nullable
    public String time;
    @Nullable
    public String amount;
    @Nullable
    public String paymentStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    @Nullable
    public LocalDateTime paymentDate;
    @Nullable
    public String paymentMethod;
    @Nullable
    public Integer patientId;
    public Integer testTypeId;
    public String testName;
    public String patientName;
}
