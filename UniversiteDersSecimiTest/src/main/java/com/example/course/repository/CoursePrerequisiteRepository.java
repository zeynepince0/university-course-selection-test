package com.example.course.repository;

import com.example.course.entity.Course;
import com.example.course.entity.CoursePrerequisite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursePrerequisiteRepository extends JpaRepository<CoursePrerequisite, Long> {

    List<CoursePrerequisite> findByCourse(Course course);
}

