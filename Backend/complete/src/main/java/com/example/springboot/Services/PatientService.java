package com.example.springboot.Services;

import com.example.springboot.DTOs.AppointmentDto;
import com.example.springboot.DTOs.UserDto;
import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.User;
import com.example.springboot.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<UserDto> getAllPatients()
    {
        List<UserDto> userDtoList = new ArrayList<>();;
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.username = user.getUsername();
            userDto.name = user.getName();
            userDto.nic = user.getNic();
            userDto.gender = user.getGender();
            userDto.email = user.getEmail();
            userDto.type = user.getType().getId();
            userDto.id = user.getId();
            userDto.password = user.getPassword();
            userDtoList.add(userDto);
        }

        return userDtoList;
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
