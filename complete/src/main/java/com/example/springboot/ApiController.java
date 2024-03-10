package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	private PatientService myService;

	@Autowired
	public ApiController(PatientService myService) {
		this.myService = myService;
	}

	@GetMapping("/")
	public String index()
	{
		String x = myService.someMethod();
		return x;
	}

	@GetMapping("/mydetails")
	public String details() {

		return "Kulaji";

	}

}
