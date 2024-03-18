package com.example.springboot.ApiController;

import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.Test;
import com.example.springboot.Entities.TestType;
import com.example.springboot.Services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
	private final TestService testService;

	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}

	//@GetMapping("/appointments/create")
	@PostMapping("/tests/create")
	public ResponseEntity<String> createTest(@RequestBody Test test)
	{
		try
		{
			Test newTest = testService.createTest(test);
			return ResponseEntity.status(HttpStatus.CREATED).body(newTest.getResultDescription().toString());
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getLocalizedMessage());
		}
	}
}
