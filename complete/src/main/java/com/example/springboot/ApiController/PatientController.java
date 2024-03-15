package com.example.springboot.ApiController;

import com.example.springboot.Entities.User;
import com.example.springboot.Entities.UserType;
import com.example.springboot.Services.AppointmentService;
import com.example.springboot.Services.PatientService;
import com.example.springboot.Services.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    private final PatientService patientService;
    private final UserTypeService userTypeService;

    @Autowired
    public PatientController(PatientService patientService, UserTypeService userTypeService) {
        this.patientService = patientService;
        this.userTypeService = userTypeService;
    }


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

}
