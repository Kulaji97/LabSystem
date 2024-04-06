package com.example.springboot.ApiController;

import com.example.springboot.DTOs.AppointmentDto;
import com.example.springboot.DTOs.TestDto;
import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.Report;
import com.example.springboot.Entities.Test;
import com.example.springboot.Entities.TestType;
import com.example.springboot.Services.AppointmentService;
import com.example.springboot.Services.PatientService;
import com.example.springboot.Services.ReportService;
import com.example.springboot.Services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class TestController {
	private final TestService testService;
	private final AppointmentService appointmentService;
	private final ReportService reportService;
	private final PatientService patientService;

	@Autowired
	public TestController(TestService testService,
						  AppointmentService appointmentService,
						  ReportService reportService,
						  PatientService patientService) {
		this.testService = testService;
		this.appointmentService = appointmentService;
		this.reportService = reportService;
		this.patientService = patientService;
	}

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

	@GetMapping("/appointment/tests/{id}")
	public ResponseEntity<TestDto> getTestForAppointment(@PathVariable String id)
	{
		try
		{
			Test test = testService.getTestByAppointmentId(Integer.parseInt(id));

			TestDto testDto = new TestDto();
			testDto.id = test.getId();
			testDto.appointmentId = test.getAppointment().getId().toString();
			testDto.technicianId = test.getTechnicianId();
			testDto.technicianName = patientService.getUserDetailsById(test.getTechnicianId()).getName();

			Report report = reportService.findReportByTestId(test.getId());
			testDto.filename = report.getName();

			return ResponseEntity.status(HttpStatus.OK).body(testDto);
		}
		catch (Exception exception)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PostMapping("/tests/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("pdfFile") MultipartFile file,
								   @RequestParam("appointment_id") String appointmentId,
								   @RequestParam("technician_id") String technicianId) throws ChangeSetPersister.NotFoundException {

		Test test = new Test();
		Appointment appointment = appointmentService.getAppointment(Integer.parseInt(appointmentId));
		test.setAppointment(appointment);
		test.setTechnicianId(Integer.parseInt(technicianId));
		testService.createTest(test);

		Test createdTest = testService.getTestByAppointmentId(appointment.getId());

		if (!file.isEmpty()) {
			// Save the file to a desired location
			try {
				Path uploadPath = Paths.get(System.getProperty("user.dir"), "/uploads/" + createdTest.getId());
				// Create the upload directory if it doesn't exist
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				// Save the file to the upload directory
				Path filePath = uploadPath.resolve(file.getOriginalFilename());
				file.transferTo(filePath.toFile());

				//create Report
				Report report = new Report();
				report.setName(file.getOriginalFilename());
				report.setPath(filePath.toString());
				report.setTest(createdTest);
				reportService.createReport(report);

				// Return the response entity with the metadata
				return ResponseEntity.ok(createdTest.getId().toString());
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
			}
		} else {
			return ResponseEntity.badRequest().body("File is empty!");
		}
	}
}
