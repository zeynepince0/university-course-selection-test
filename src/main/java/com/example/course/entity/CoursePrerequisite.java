package com.example.course.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_prerequisites")
public class CoursePrerequisite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "prerequisite_course_id")
    private Course prerequisiteCourse;

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public Course getPrerequisiteCourse() {
        return prerequisiteCourse;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setPrerequisiteCourse(Course prerequisiteCourse) {
        this.prerequisiteCourse = prerequisiteCourse;
    }
}

