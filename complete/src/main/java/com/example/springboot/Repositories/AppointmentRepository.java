package com.example.springboot.Repositories;

import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {}
