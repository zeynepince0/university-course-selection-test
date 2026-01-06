package com.example.course.repository;

import com.example.course.entity.Course;
import com.example.course.entity.Enrollment;
import com.example.course.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudent(Student student);

    long countByCourse(Course course);

    @Query("""
        select coalesce(sum(e.course.credit), 0)
        from Enrollment e
        where e.student = :student
        and e.status = 'APPROVED'
    """)
    int sumApprovedCreditsByStudent(@Param("student") Student student);
}
