package com.example.springboot.ApiController;

import com.example.springboot.DTOs.UserDto;
import com.example.springboot.Entities.User;
import com.example.springboot.Entities.UserType;
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
    public ResponseEntity<UserDto> createPatient(@RequestBody UserDto userDto)
    {
        try
        {
            UserType userType = userTypeService.getUserType(userDto.type);
            User user = new User();
            user.setUsername(userDto.username);
            user.setGender(userDto.gender);
            user.setName(userDto.name);
            user.setNic(userDto.nic);
            user.setEmail(userDto.email);
            user.setPassword(userDto.password);
            user.setType(userType);
            patientService.savePatient(user);

            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
        catch (Exception exception)
        {
            System.out.println(exception);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/patients/all")
    public ResponseEntity<List<UserDto>> getAllPatients()
    {
        try
        {
            List<UserDto> users = patientService.getAllPatients();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id)
    {
        try
        {
            User user = patientService.getUserDetailsById(Integer.parseInt(id));
            UserDto userDto = new UserDto();
            userDto.id = user.getId();
            userDto.email = user.getEmail();
            userDto.nic = user.getNic();
            userDto.gender = user.getGender();
            userDto.name = user.getName();
            userDto.type = user.getType().getId();
            userDto.username = user.getUsername();
            userDto.password = user.getPassword();
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable String id,
                                               @RequestBody UserDto newUser)
    {
        try
        {
            User user = patientService.getUserDetailsById(Integer.parseInt(id));
            user.setName(newUser.name);
            user.setNic(newUser.nic);
            user.setUsername(newUser.username);
            user.setPassword(newUser.password);
            user.setEmail(newUser.email);
            patientService.savePatient(user);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable String id)
    {
        try
        {
            User user = patientService.getUserDetailsById(Integer.parseInt(id));
            patientService.deleteUser(user);

            return ResponseEntity.status(HttpStatus.OK).body(user);
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
            List<User> users = patientService.authenticateUser(user.getUsername(), user.getPassword());
            if (users.size()> 0) {
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
