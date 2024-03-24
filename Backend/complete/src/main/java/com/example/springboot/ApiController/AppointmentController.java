package com.example.springboot.ApiController;

import com.example.springboot.DTOs.AppointmentDto;
import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.User;
import com.example.springboot.Services.AppointmentService;
import com.example.springboot.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class AppointmentController {
	private final AppointmentService appointmentService;
	private final PatientService patientService;

	@Autowired
	public AppointmentController(AppointmentService appointmentService, PatientService patientService) {
		this.appointmentService = appointmentService;
		this.patientService = patientService;
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

	@GetMapping("user/appointment/{id}")
	public ResponseEntity<List<AppointmentDto>> getAppointment(@PathVariable String id)
	{
		try
		{
			List<AppointmentDto> appointments = appointmentService.getAppointmentByPatientId(Integer.parseInt(id));
			return ResponseEntity.status(HttpStatus.CREATED).body(appointments);
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}
