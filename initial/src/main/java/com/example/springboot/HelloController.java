package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

//	@GetMapping("/")
//	public String index() {
//
//		String v = "Hi";
//		return v;
//
//	}

	@GetMapping("/mydetails")
	public String details() {


		return "Kulaji";

	}

}
