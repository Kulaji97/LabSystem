package com.example.springboot;

import com.example.springboot.Entities.User;
import com.example.springboot.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {

	private final PatientService patientService;

	@Autowired
	public ApiController(PatientService patientService) {
		this.patientService = patientService;
	}


	@PostMapping("/patients/create")
	public ResponseEntity<String> createPatient(@RequestBody User user)
	{
		try
		{
			patientService.savePatient(user);
			return ResponseEntity.status(HttpStatus.CREATED).body("Patient saved successfully");
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
		}
	}

	@GetMapping("/")
	public String getAllPatients()
	{
		String x = patientService.someMethod();
		return x;
	}

}
