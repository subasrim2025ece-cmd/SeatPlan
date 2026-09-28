package com.example.SeatPlan.student.controller;

import com.example.SeatPlan.student.entity.Student;
import com.example.SeatPlan.student.service.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // CREATE STUDENT
    @PostMapping
    public Student createStudent(
            @RequestBody Student student) {

        return studentService.createStudent(student);
    }

    // GET ALL STUDENTS
    @GetMapping
    public List<Student> getAllStudents() {

        return studentService.getAllStudents();
    }

    // GET STUDENT BY DATABASE ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    studentService.getStudentById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    // GET STUDENT BY REGISTER NUMBER
    @GetMapping("/{registerNumber}")
    public ResponseEntity<Student> getStudentByRegisterNumber(
            @PathVariable String registerNumber) {

        try {

            return ResponseEntity.ok(
                    studentService.getStudentByRegisterNumber(
                            registerNumber
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE STUDENT
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        try {

            return ResponseEntity.ok(
                    studentService.updateStudent(
                            id,
                            student
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    // DELETE STUDENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {

        try {

            studentService.deleteStudent(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }
}