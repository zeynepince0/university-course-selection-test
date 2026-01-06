package com.example.course.service.impl;

import com.example.course.dto.EnrollmentResponse;
import com.example.course.entity.Course;
import com.example.course.entity.Enrollment;
import com.example.course.entity.EnrollmentStatus;
import com.example.course.entity.Student;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.EnrollmentRepository;
import com.example.course.repository.StudentRepository;
import com.example.course.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              CourseRepository courseRepository,
                              EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<EnrollmentResponse> selectCourses(String studentNumber, List<String> courseCodes) {

        Student student = studentRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Mevcut alınmış kredi (ENROLLMENT üzerinden)
        int usedCredit = enrollmentRepository.findByStudent(student)
                .stream()
                .mapToInt(e -> e.getCourse().getCredit())
                .sum();

        List<EnrollmentResponse> responses = new ArrayList<>();

        for (String code : courseCodes) {

            Course course = courseRepository.findByCode(code)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            // 1️⃣ Class year kontrolü
            if (student.getClassYear() < course.getClassYear()) {
                throw new IllegalStateException("Class year not sufficient");
            }

            // 2️⃣ Kontenjan kontrolü
            long currentCount = enrollmentRepository.countByCourse(course);
            if (currentCount >= course.getQuota()) {
                throw new IllegalStateException("Course quota full");
            }

            // 3️⃣ Kredi kontrolü
            if (usedCredit + course.getCredit() > student.getMaxCredit()) {
                throw new IllegalStateException("Credit limit exceeded");
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setStatus(EnrollmentStatus.PENDING);
            enrollment.setCreatedAt(LocalDateTime.now());

            Enrollment saved = enrollmentRepository.save(enrollment);
            usedCredit += course.getCredit();

            responses.add(new EnrollmentResponse(
                    saved.getId(),
                    course.getCode(),
                    course.getName(),
                    saved.getStatus().name()
            ));
        }

        return responses;
    }

    @Override
    public List<EnrollmentResponse> getEnrollments(String studentNumber) {

        Student student = studentRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return enrollmentRepository.findByStudent(student)
                .stream()
                .map(e -> new EnrollmentResponse(
                        e.getId(),
                        e.getCourse().getCode(),
                        e.getCourse().getName(),
                        e.getStatus().name()
                ))
                .toList();
    }
}
