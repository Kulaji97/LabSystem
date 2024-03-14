package com.example.springboot;

import com.example.springboot.Entities.Appointment;
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

import java.io.Console;
import java.util.List;


@RestController
public class ApiController {

	private final PatientService patientService;
	private final UserTypeService userTypeService;
	private final AppointmentService appointmentService;

	@Autowired
	public ApiController(PatientService patientService, UserTypeService userTypeService, AppointmentService appointmentService) {
		this.patientService = patientService;
		this.userTypeService = userTypeService;
		this.appointmentService = appointmentService;
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

	//@GetMapping("/appointments/create")
	@PostMapping("/appointments/create")
	public ResponseEntity<String> getAppointment(@RequestBody Appointment appointment)
	{
		try
		{
			Appointment newAppointment = appointmentService.createAppointment(appointment);
			return ResponseEntity.status(HttpStatus.CREATED).body(newAppointment.getTime().toString());
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getLocalizedMessage());
		}
	}

}
