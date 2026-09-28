package com.example.SeatPlan.student.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "register_number")
    private String registerNumber;

    @Column(name = "department")
    private String department;

    @Column(name = "class_name")
    private String className;

    @Column(name = "section")
    private String section;

    public Student() {
    }

    public Student(
            String studentName,
            String registerNumber,
            String department,
            String className,
            String section) {

        this.studentName = studentName;
        this.registerNumber = registerNumber;
        this.department = department;
        this.className = className;
        this.section = section;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}