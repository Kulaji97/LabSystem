package com.example.springboot.Services;

import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.Test;
import com.example.springboot.Entities.TestType;
import com.example.springboot.Entities.User;
import com.example.springboot.Entities.UserType;
import com.example.springboot.Repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;
    private final DatabaseSingleton databaseSingleton;

    public TestService() {
        this.databaseSingleton = DatabaseSingleton.getInstance();
    }

    public Test createTest(Test test) {
        Test newTest = testRepository.save(test);
        return newTest;
    }

    public Test getTestByAppointmentId(int appointmentId) {
        Test test = testRepository.findByAppointmentId(appointmentId).getFirst();
        return test;
    }
}
