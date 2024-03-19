package com.example.springboot.Repositories;

import com.example.springboot.Entities.Appointment;
import com.example.springboot.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{

    @Query(value = "SELECT * FROM USER WHERE username = ?1 AND password >= ?2", nativeQuery = true)
    public List<User> findByUsernameAndPassword(String username, String password);

}
