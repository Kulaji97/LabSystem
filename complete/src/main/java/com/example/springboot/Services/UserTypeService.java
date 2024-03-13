package com.example.springboot.Services;

import com.example.springboot.DatabaseConnection.DatabaseSingleton;
import com.example.springboot.Entities.User;
import com.example.springboot.Entities.UserType;
import com.example.springboot.Repositories.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class UserTypeService {

    @Autowired
    private UserTypeRepository userTypeRepository;
    private final DatabaseSingleton databaseSingleton;

    public UserTypeService() {
        this.databaseSingleton = DatabaseSingleton.getInstance();
    }

    public UserType getUserType(User user) throws ChangeSetPersister.NotFoundException {
        UserType userType = userTypeRepository.findById(user.getType().getId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        return userType;
    }
}
