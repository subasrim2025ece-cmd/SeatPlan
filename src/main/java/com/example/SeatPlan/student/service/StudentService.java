
        package com.example.SeatPlan.student.service;

import com.example.SeatPlan.student.entity.Student;
import com.example.SeatPlan.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(
            StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    // CREATE
    public Student createStudent(Student student) {

        return studentRepository.save(student);
    }

    // GET ALL
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    // GET BY DATABASE ID
    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with ID: " + id
                        )
                );
    }

    // GET BY REGISTER NUMBER
    public Student getStudentByRegisterNumber(
            String registerNumber) {

        return studentRepository
                .findByRegisterNumber(registerNumber)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with register number: "
                                        + registerNumber
                        )
                );
    }

    // UPDATE
    public Student updateStudent(
            Long id,
            Student student) {

        Student existingStudent =
                getStudentById(id);

        existingStudent.setStudentName(
                student.getStudentName()
        );

        existingStudent.setRegisterNumber(
                student.getRegisterNumber()
        );

        existingStudent.setDepartment(
                student.getDepartment()
        );

        existingStudent.setClassName(
                student.getClassName()
        );

        existingStudent.setSection(
                student.getSection()
        );

        return studentRepository.save(
                existingStudent
        );
    }

    // DELETE
    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {

            throw new RuntimeException(
                    "Student not found with ID: " + id
            );
        }

        studentRepository.deleteById(id);
    }
}
