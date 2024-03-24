package com.example.springboot.Services;

import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.User;
import com.example.springboot.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private UserRepository userRepository;
    private final DatabaseSingleton databaseSingleton;

    public PatientService() {
        this.databaseSingleton = DatabaseSingleton.getInstance();
    }

    public User savePatient(User user)
    {
        return userRepository.save(user);
    }
    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }

    public List<User> getAllPatients()
    {
        return userRepository.findAll();
    }
    public List<User> authenticateUser(String username, String password)
    {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User getUserDetailsById(int id)
    {
        return userRepository.findById(id).get();
    }
}
