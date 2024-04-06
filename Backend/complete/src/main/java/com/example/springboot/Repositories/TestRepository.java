package com.example.springboot.Repositories;

import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {

    @Query(value = "SELECT * FROM TEST WHERE appointmentid = ?1", nativeQuery = true)
    public List<Test> findByAppointmentId(int appointmentId);

}

