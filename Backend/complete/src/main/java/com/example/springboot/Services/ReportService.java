package com.example.springboot.Services;

import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.*;
import com.example.springboot.Repositories.ReportRepository;
import com.example.springboot.Repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    private final DatabaseSingleton databaseSingleton;

    public ReportService() {
        this.databaseSingleton = DatabaseSingleton.getInstance();
    }

    public Report createReport(Report report) {
        Report newReport = reportRepository.save(report);
        return newReport;
    }

    public Report findReportByTestId(int testId) {
        Report newReport = reportRepository.findByTestId(testId).getFirst();
        return newReport;
    }

}
