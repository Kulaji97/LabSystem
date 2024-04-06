package com.example.springboot.Repositories;

import com.example.springboot.Entities.Report;
import com.example.springboot.Entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
    @Query(value = "SELECT * FROM REPORT WHERE testid = ?1", nativeQuery = true)
    public List<Report> findByTestId(int testid);
}

