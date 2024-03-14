package com.example.springboot.Services;

import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.TestType;
import com.example.springboot.Entities.User;
import com.example.springboot.Entities.UserType;
import com.example.springboot.Repositories.AppointmentRepository;
import com.example.springboot.Repositories.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    private final DatabaseSingleton databaseSingleton;
    private final TestTypeService testTypeService;

    public AppointmentService(TestTypeService testTypeService) {
        this.testTypeService = testTypeService;
        this.databaseSingleton = DatabaseSingleton.getInstance();
    }

    public Appointment getAppointment(Appointment patientAppointment) throws ChangeSetPersister.NotFoundException {
        Appointment appointment = appointmentRepository.findById(patientAppointment.getId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return appointment;
    }

    public int generateAppointmentNumber(int testTypeId, LocalDate date)
    {
        List<Appointment> appointments = appointmentRepository.findByTestTypeAndDate(testTypeId, date);
        int maxNumber = appointments!= null ? appointments.stream().max(Comparator.comparingInt(Appointment::getNumber)).get().getNumber() : 0;
        return maxNumber + 1;
    }

    public Appointment createAppointment(Appointment appointment)
    {
        //get the maximum number based on the test type and date
        int newAppointmentNumber = generateAppointmentNumber(appointment.getTestType().getId(), appointment.getTime().toLocalDate());

        //patient appointment time = date + 8hr (lab opening time) + (max_number * 30min)
        LocalDateTime newAppointmentTime = appointment.getTime().plusMinutes((8*60) + (newAppointmentNumber* 30L));

        //create new appointment
        Appointment newAppointment = appointment;
        newAppointment.setTime(newAppointmentTime);
        newAppointment.setNumber(newAppointmentNumber);

        return appointmentRepository.save(newAppointment);
    }
}
