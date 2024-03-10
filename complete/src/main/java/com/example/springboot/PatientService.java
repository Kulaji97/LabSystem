package com.example.springboot;

import org.springframework.stereotype.Service;

@Service
public class PatientService {

    public String someMethod() {
        // Common logic for endpoint1

        return "Response from someMethod";
    }

    public String anotherMethod() {
        // Common logic for endpoint2
        return "Response from anotherMethod";
    }
}
