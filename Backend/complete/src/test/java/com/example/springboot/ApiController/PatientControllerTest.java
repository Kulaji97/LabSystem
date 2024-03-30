package com.example.springboot.ApiController;

import com.example.springboot.DTOs.AppointmentDto;
import com.example.springboot.DTOs.UserDto;
import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.UserType;
import com.example.springboot.Repositories.AppointmentRepository;
import com.example.springboot.Services.AppointmentService;
import com.example.springboot.Services.UserTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.springboot.Entities.User;
import com.example.springboot.Services.PatientService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAppointments() {
        //Mock data
        Appointment appointment1 = new Appointment();
        appointment1.setId(1);
        appointment1.setAmount(BigDecimal.valueOf(100.00));
        // Set other appointment properties

        Appointment appointment2 = new Appointment();
        appointment2.setId(2);
        appointment2.setAmount(BigDecimal.valueOf(200.00));
        // Set other appointment properties

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment1);
        appointments.add(appointment2);

        // Mock the behavior of appointmentRepository.findAll() to return the mock appointments
        when(appointmentRepository.findAll()).thenReturn(appointments);

        // Call the method under test
        List<AppointmentDto> result = appointmentService.getAll();

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result size matches the number of mock appointments
        assertEquals(2, result.size());

        // Assert that the appointmentDto properties are correctly mapped from the appointment objects
        AppointmentDto appointmentDto1 = result.get(0);
        assertEquals(1, appointmentDto1.id);
        assertEquals("100.0", appointmentDto1.amount);
        // Assert other properties

        AppointmentDto appointmentDto2 = result.get(1);
        assertEquals(2, appointmentDto2.id);
        assertEquals("200.0", appointmentDto2.amount);
        // Assert other properties
    }
}