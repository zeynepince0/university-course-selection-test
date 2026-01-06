package com.example.course.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String name;

    private int credit;
    private int quota;
    private int classYear;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    private List<CoursePrerequisite> prerequisites;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public List<CoursePrerequisite> getPrerequisites() {
        return prerequisites;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }


    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void setPrerequisites(List<CoursePrerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }
    public int getQuota() {
        return quota;
    }
    public void setQuota(int quota) {
        this.quota = quota;
    }
    public int getClassYear() {
        return classYear;
    }
    public void setClassYear(int classYear) {
        this.classYear = classYear;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
