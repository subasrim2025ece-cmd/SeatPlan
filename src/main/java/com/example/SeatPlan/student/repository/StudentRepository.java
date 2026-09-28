package com.example.SeatPlan.student.repository;

import com.example.SeatPlan.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Optional<Student> findByRegisterNumber(
            String registerNumber
    );
}