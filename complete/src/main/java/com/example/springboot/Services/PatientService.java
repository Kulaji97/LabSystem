package com.example.springboot.Services;

import com.example.springboot.Entities.User;
import com.example.springboot.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private UserRepository userRepository;
    public String someMethod() {
        // Common logic for endpoint1

        return "Response from someMethod";
    }

    public String anotherMethod(User user) {
        // Common logic for endpoint2
        return "Response from anotherMethod";
    }

    public User savePatient(User user)
    {
        return userRepository.save(user);
    }
}
