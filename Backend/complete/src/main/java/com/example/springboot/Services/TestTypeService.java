package com.example.springboot.Services;

import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.TestType;
import com.example.springboot.Repositories.TestTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class TestTypeService {

    @Autowired
    private TestTypeRepository testTypeRepository;
    private final DatabaseSingleton databaseSingleton;

    public TestTypeService() {
        this.databaseSingleton = DatabaseSingleton.getInstance();
    }

    public TestType getUserType(int id) throws ChangeSetPersister.NotFoundException {
        TestType userType = testTypeRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        return userType;
    }
}
