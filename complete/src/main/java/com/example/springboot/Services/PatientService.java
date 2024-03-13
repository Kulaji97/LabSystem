package com.example.springboot.Services;

import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.User;
import com.example.springboot.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private UserRepository userRepository;
    private final DatabaseSingleton databaseSingleton;

    public PatientService() {
        this.databaseSingleton = DatabaseSingleton.getInstance();
    }
    public String someMethod() {
        return "Response from someMethod";
    }

    public String anotherMethod(User user) { return "Response from anotherMethod"; }

    public User savePatient(User user)
    {
        return userRepository.save(user);
    }
}
