package com.example.springboot.Services;

import com.example.springboot.DTOs.AppointmentDto;
import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.TestType;
import com.example.springboot.Enum.PaymentStatus;
import com.example.springboot.Repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    private final DatabaseSingleton databaseSingleton;
    private final TestTypeService testTypeService;
    private final PatientService patientService;

    public AppointmentService(TestTypeService testTypeService,
                              PatientService patientService) {
        this.testTypeService = testTypeService;
        this.patientService = patientService;
        this.databaseSingleton = DatabaseSingleton.getInstance();
    }

    public Appointment getAppointment(Integer id) throws ChangeSetPersister.NotFoundException {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return appointment;
    }
    public AppointmentDto getAppointmentById(Integer id) throws ChangeSetPersister.NotFoundException {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.id = appointment.getId();
        appointmentDto.amount = appointment.getAmount().toString();
        appointmentDto.doctorId = appointment.getDoctorId();
        appointmentDto.number = appointment.getNumber();
        appointmentDto.time = appointment.getTime().toString();
        appointmentDto.patientId = appointment.getPatient().getId();
        appointmentDto.paymentDate = appointment.getPaymentDate();
        appointmentDto.paymentStatus = getPaymentStatusName(appointment.getPaymentStatus());
        appointmentDto.testTypeId = appointment.getTestType().getId();
        appointmentDto.testName = appointment.getTestType().getType();

        return appointmentDto;
    }

    public String getPaymentStatusName(Integer status){
        if (status == (PaymentStatus.AWAITING_PAYMENT)) {
            return "Awaiting Payment";
        }
        else if (status == (PaymentStatus.PAID)) {
            return "Paid";
        } else {
            return "Partial Payment";
        }
    }

    public List<AppointmentDto> getAppointmentByPatientId(Integer patientId) {
        List<Appointment> appointments = appointmentRepository.findAppointmentByPatientId(patientId);
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        try {
            if (appointments.size() > 0){
                Collections.sort(appointments, (o1, o2) -> o1.getTime().compareTo( o2.getTime()));
                for (Appointment appointment : appointments)
                {
                    AppointmentDto appointmentDto = new AppointmentDto();
                    appointmentDto.id = appointment.getId();
                    appointmentDto.amount = appointment.getAmount().toString();
                    appointmentDto.doctorId = appointment.getDoctorId();
                    appointmentDto.number = appointment.getNumber();
                    appointmentDto.time = appointment.getTime().toString();
                    appointmentDto.patientId = appointment.getPatient().getId();
                    appointmentDto.paymentDate = appointment.getPaymentDate();
                    appointmentDto.paymentStatus = getPaymentStatusName(appointment.getPaymentStatus());
                    appointmentDto.testTypeId = appointment.getTestType().getId();
                    appointmentDto.testName = appointment.getTestType().getType();
                    appointmentDtos.add(appointmentDto);
                }
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception.getLocalizedMessage());
        }

        return appointmentDtos;
    }

    public List<AppointmentDto> getAll() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        try {
            if (appointments.size() > 0){
                Collections.sort(appointments, (o1, o2) -> o1.getTime().compareTo( o2.getTime()));
                for (Appointment appointment : appointments)
                {
                    AppointmentDto appointmentDto = new AppointmentDto();
                    appointmentDto.id = appointment.getId();
                    appointmentDto.patientName = appointment.getPatient().getName();
                    appointmentDto.amount = appointment.getAmount().toString();
                    appointmentDto.doctorId = appointment.getDoctorId();
                    appointmentDto.number = appointment.getNumber();
                    appointmentDto.time = appointment.getTime().toString();
                    appointmentDto.patientId = appointment.getPatient().getId();
                    appointmentDto.paymentDate = appointment.getPaymentDate();
                    appointmentDto.paymentStatus = getPaymentStatusName(appointment.getPaymentStatus());
                    appointmentDto.testTypeId = appointment.getTestType().getId();
                    appointmentDto.testName = appointment.getTestType().getType();
                    appointmentDtos.add(appointmentDto);
                }
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception.getLocalizedMessage());
        }

        return appointmentDtos;
    }

    public int generateAppointmentNumber(int testTypeId, LocalDate date)
    {
        List<Appointment> appointments = appointmentRepository.findByTestTypeAndDate(testTypeId, date, date.plusDays(1));

        int maxNumber = (appointments!= null && appointments.size()>0)
                ? appointments.stream().max(Comparator.comparingInt(Appointment::getNumber)).get().getNumber()
                : 0;

        return maxNumber + 1;
    }

    public Appointment createAppointment(AppointmentDto appointment) throws ChangeSetPersister.NotFoundException {
        //get the maximum number based on the test type and date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime appointmentDate = LocalDateTime.parse(appointment.time, formatter);

        int newAppointmentNumber = generateAppointmentNumber(appointment.testTypeId, appointmentDate.toLocalDate());

        //patient appointment time = date + 8hr (lab opening time) + (max_number * 30min)
        LocalDateTime newAppointmentTime = appointmentDate.plusMinutes((8*60) + (newAppointmentNumber* 30L));

        //create new appointment
        Appointment newAppointment = new Appointment();
        newAppointment.setPatient(patientService.getUserDetailsById(appointment.patientId));

        TestType testType = testTypeService.getTestType(appointment.testTypeId);
        newAppointment.setTestType(testType);

        newAppointment.setAmount(convertStringToDecimal(appointment.amount));
        newAppointment.setPaymentStatus(PaymentStatus.AWAITING_PAYMENT);
        newAppointment.setTime(newAppointmentTime);
        newAppointment.setNumber(newAppointmentNumber);
        newAppointment.setDoctorId(appointment.doctorId);
        newAppointment.setPaymentMethod("CreditCard");
        newAppointment.setPaymentDate(null);

        return appointmentRepository.save(newAppointment);
    }

    public Appointment updatePaymentDetails(AppointmentDto newAppointment) throws ChangeSetPersister.NotFoundException {
        Appointment appointment = getAppointment(newAppointment.id);
        appointment.setPaymentDate(LocalDateTime.now());
        appointment.setPaymentMethod(newAppointment.paymentMethod);
        appointment.setPaymentStatus(PaymentStatus.PAID);

        return appointmentRepository.save(appointment);
    }

    public BigDecimal convertStringToDecimal(String stringWithComma)
    {
        // Remove comma from the string
        String stringWithoutComma = stringWithComma.replaceAll(",", "");

        // Parse the string into a BigDecimal
        BigDecimal bigDecimalValue = new BigDecimal(stringWithoutComma);

        return bigDecimalValue;
    }

}
