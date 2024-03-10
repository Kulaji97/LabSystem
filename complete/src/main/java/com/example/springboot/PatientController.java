package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class PatientController {

    public String GetPatient()
    {
        String x = "this patient";
        return x;
    }



}
