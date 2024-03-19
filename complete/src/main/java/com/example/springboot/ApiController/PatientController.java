package com.example.springboot.ApiController;

import com.example.springboot.Entities.User;
import com.example.springboot.Entities.UserType;
import com.example.springboot.Services.AppointmentService;
import com.example.springboot.Services.PatientService;
import com.example.springboot.Services.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PatientController {

    private final PatientService patientService;
    private final UserTypeService userTypeService;

    @Autowired
    public PatientController(PatientService patientService, UserTypeService userTypeService) {
        this.patientService = patientService;
        this.userTypeService = userTypeService;
    }


    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/patients/create")
    public ResponseEntity<String> createPatient(@RequestBody User user)
    {
        try
        {
            System.out.println(user.getName());
            UserType userType = userTypeService.getUserType(user);

            user.setType(userType);
            user = patientService.savePatient(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user.getId().toString());
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/patients/all")
    public ResponseEntity<List<User>> getAllPatients()
    {
        try
        {
            List<User> users = patientService.getAllPatients();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/patients/update")
    public ResponseEntity<List<User>> updateUser()
    {
        try
        {
            List<User> users = patientService.getAllPatients();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/users/authenticate")
    public ResponseEntity<User> authenticateUser(@RequestBody User user)
    {
        try
        {
            System.out.println(user.getUsername() + " " + user.getPassword());
            System.out.println(LocalDateTime.now());
            List<User> users = patientService.authenticateUser(user.getUsername(), user.getPassword());
            if (users.stream().count() > 0) {
                System.out.println("1 is there");
                System.out.println(users.getFirst().getName());
                return ResponseEntity.status(HttpStatus.OK).body(users.getFirst());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
