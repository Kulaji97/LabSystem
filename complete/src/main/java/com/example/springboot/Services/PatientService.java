package com.example.springboot.Services;

import com.example.springboot.Entities.Patient;
import com.example.springboot.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    public String someMethod() {
        // Common logic for endpoint1

        return "Response from someMethod";
    }

    public String anotherMethod(Patient patient) {
        // Common logic for endpoint2
        return "Response from anotherMethod";
    }

    public Patient savePatient(Patient patient)
    {
        return patientRepository.save(patient);
    }
}
