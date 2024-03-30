package com.example.springboot.ApiController;

import com.example.springboot.DTOs.AppointmentDto;
import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.User;
import com.example.springboot.Services.AppointmentService;
import com.example.springboot.Services.EmailService;
import com.example.springboot.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class AppointmentController {
	private final AppointmentService appointmentService;
	private final PatientService patientService;
	private final EmailService emailService;

	@Autowired
	public AppointmentController(AppointmentService appointmentService, PatientService patientService, EmailService emailService) {
		this.appointmentService = appointmentService;
		this.patientService = patientService;
		this.emailService = emailService;
	}

	//@GetMapping("/appointments/create")
	@PostMapping("/appointments/create")
	public ResponseEntity<String> createAppointment(@RequestBody AppointmentDto appointment)
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
	@GetMapping("/appointments/all")
	public ResponseEntity<List<AppointmentDto>> createAppointment()
	{
		try
		{
			List<AppointmentDto> appointments = appointmentService.getAll();
			return ResponseEntity.status(HttpStatus.CREATED).body(appointments);
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PostMapping("/patients/pay")
	public ResponseEntity<String> recievePayment(@RequestBody AppointmentDto appointment) throws ChangeSetPersister.NotFoundException {
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

	@GetMapping("/appointment/{id}")
	public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable String id)
	{
		try
		{
			AppointmentDto appointment = appointmentService.getAppointmentById(Integer.parseInt(id));
			return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200/")
	@PutMapping("/appointment/{id}")
	public ResponseEntity<String> updateAppointmentById(@PathVariable String id,
											   @RequestBody AppointmentDto appointment)
	{
		try
		{
			Appointment updatedAppointment = appointmentService.updatePaymentDetails(appointment);

			Context context = new Context();
			context.setVariable("date", updatedAppointment.getTime());
			context.setVariable("name", updatedAppointment.getPatient().getName());
			context.setVariable("email", updatedAppointment.getPatient().getEmail());
			context.setVariable("test_name", updatedAppointment.getTestType().getType());
			context.setVariable("test_type", updatedAppointment.getTestType().getId());
			context.setVariable("test_amount", appointment.amount);
			context.setVariable("total_amount", (updatedAppointment.getAmount().doubleValue()+10.00));
			context.setVariable("payment_method", "CREDIT CARD");

			String emailHeader = "ABC Labs Appointment Receipt - " + updatedAppointment.getNumber();
			emailService.sendEmailWithHtmlTemplate(appointment.email, emailHeader, "email-template", context);

			return ResponseEntity.status(HttpStatus.CREATED).body(updatedAppointment.getPaymentStatus().toString());
		}
		catch (Exception exception)
		{
			System.out.println(exception.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}
