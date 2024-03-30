package com.example.springboot.ApiController;

import com.example.springboot.DTOs.UserDto;
import com.example.springboot.Entities.UserType;
import com.example.springboot.Services.UserTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.springboot.Entities.User;
import com.example.springboot.Services.PatientService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private UserTypeService userTypeService;

    @Test
    public void testCreatePatient() throws Exception {
        // Prepare a UserDto object
        UserDto userDto = new UserDto();
        userDto.name = "John Doe";
        userDto.gender = "Male";
        userDto.nic = "1234567890";
        userDto.email = "john.doe@example.com";
        userDto.password = "password";
        userDto.username = "johndoe";

        // Prepare a UserType object
        UserType userType = new UserType();
        userType.setId(1);
        userType.setType("Test");

        // Mock the behavior of patientService
        User savedPatient = new User();
        savedPatient.setId(1); // Assuming an ID is set upon saving
        when(patientService.savePatient(any(User.class))).thenReturn(savedPatient);

        // Perform POST request and verify the response
        mockMvc.perform(post("/patients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists()); // Assuming your UserDto has an 'id' field
    }

    // Utility method to convert objects to JSON string
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}