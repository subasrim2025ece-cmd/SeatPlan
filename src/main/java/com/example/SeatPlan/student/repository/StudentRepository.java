package com.example.SeatPlan.student.repository;

import com.example.SeatPlan.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}