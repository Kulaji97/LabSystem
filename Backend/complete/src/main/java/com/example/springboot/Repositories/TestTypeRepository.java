package com.example.springboot.Repositories;

import com.example.springboot.Entities.TestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTypeRepository extends JpaRepository<TestType,Integer> {
}

