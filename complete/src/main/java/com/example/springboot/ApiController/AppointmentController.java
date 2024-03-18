package com.example.springboot.ApiController;

import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.User;
import com.example.springboot.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppointmentController {
	private final AppointmentService appointmentService;

	@Autowired
	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	//@GetMapping("/appointments/create")
	@PostMapping("/appointments/create")
	public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment)
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

	@PostMapping("/patients/pay")
	public ResponseEntity<String> recievePayment(@RequestBody Appointment appointment) throws ChangeSetPersister.NotFoundException {
		Appointment updatedAppointment = appointmentService.updatePaymentDetails(appointment);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedAppointment.getPaymentStatus().toString());
	}
}
