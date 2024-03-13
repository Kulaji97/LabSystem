package com.example.springboot.Services;

import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.User;
import com.example.springboot.Entities.UserType;
import com.example.springboot.Repositories.AppointmentRepository;
import com.example.springboot.Repositories.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    private final DatabaseSingleton databaseSingleton;

    public AppointmentService() {
        this.databaseSingleton = DatabaseSingleton.getInstance();
    }

    public Appointment getAppointment(Appointment patientAppointment) throws ChangeSetPersister.NotFoundException {
        Appointment appointment = appointmentRepository.findById(patientAppointment.getId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        return appointment;
    }

    public int generateAppointmentNumber(int doctorId, LocalDate date)
    {
        //Appointment appointment = appointmentRepository.findById();
        return 1;
    }
}
