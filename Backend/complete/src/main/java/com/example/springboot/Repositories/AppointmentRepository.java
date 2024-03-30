package com.example.springboot.Repositories;

import com.example.springboot.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer>
{

     //https://docs.spring.io/spring-data/jpa/docs/1.11.1.RELEASE/reference/html/#jpa.query-methods.at-query
     @Query(value = "SELECT * FROM APPOINTMENT WHERE test_typeid = ?1 AND time >= ?2 AND time<?3", nativeQuery = true)
     public List<Appointment> findByTestTypeAndDate(int testTypeId, LocalDate date1, LocalDate date2);

     @Query(value = "SELECT * FROM APPOINTMENT WHERE patientid = ?1", nativeQuery = true)
     public List<Appointment> findAppointmentByPatientId(int patientid);

}
