package com.example.course.unit;

import com.example.course.dto.EnrollmentResponse;
import com.example.course.entity.Course;
import com.example.course.entity.Enrollment;
import com.example.course.entity.EnrollmentStatus;
import com.example.course.entity.Student;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.EnrollmentRepository;
import com.example.course.repository.StudentRepository;
import com.example.course.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    CourseRepository courseRepository;

    @Mock
    EnrollmentRepository enrollmentRepository;

    @InjectMocks
    StudentServiceImpl studentService;

    @Test
    void selectCourse_success() {
        Student s = new Student();
        s.setStudentNumber("S1");
        s.setClassYear(3);
        s.setMaxCredit(30);

        Course c = new Course();
        c.setCode("C1");
        c.setCredit(5);
        c.setQuota(10);
        c.setClassYear(1);

        Enrollment saved = new Enrollment();
        saved.setId(1L);
        saved.setCourse(c);
        saved.setStatus(EnrollmentStatus.PENDING);

        when(studentRepository.findByStudentNumber("S1"))
                .thenReturn(Optional.of(s));
        when(courseRepository.findByCode("C1"))
                .thenReturn(Optional.of(c));
        when(enrollmentRepository.countByCourse(c))
                .thenReturn(0L);
        when(enrollmentRepository.findByStudent(s))
                .thenReturn(List.of());
        when(enrollmentRepository.save(any()))
                .thenReturn(saved);

        List<EnrollmentResponse> res =
                studentService.selectCourses("S1", List.of("C1"));

        assertEquals(1, res.size());
        assertEquals("C1", res.get(0).getCourseCode());
    }

    @Test
    void studentNotFound_throws() {
        when(studentRepository.findByStudentNumber("X"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> studentService.selectCourses("X", List.of("C1")));
    }

    @Test
    void courseNotFound_throws() {
        Student s = new Student();

        when(studentRepository.findByStudentNumber("S1"))
                .thenReturn(Optional.of(s));
        when(courseRepository.findByCode("C1"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> studentService.selectCourses("S1", List.of("C1")));
    }

    @Test
    void quotaFull_throws() {
        Student s = new Student();
        s.setClassYear(3);

        Course c = new Course();
        c.setQuota(1);
        c.setClassYear(1);

        when(studentRepository.findByStudentNumber("S1"))
                .thenReturn(Optional.of(s));
        when(courseRepository.findByCode("C1"))
                .thenReturn(Optional.of(c));
        when(enrollmentRepository.countByCourse(c))
                .thenReturn(1L);

        assertThrows(IllegalStateException.class,
                () -> studentService.selectCourses("S1", List.of("C1")));
    }

    @Test
    void creditLimitExceeded_throws() {
        Student s = new Student();
        s.setMaxCredit(5);
        s.setClassYear(3);

        Course c = new Course();
        c.setCredit(6);
        c.setClassYear(1);

        when(studentRepository.findByStudentNumber("S1"))
                .thenReturn(Optional.of(s));
        when(courseRepository.findByCode("C1"))
                .thenReturn(Optional.of(c));
        when(enrollmentRepository.findByStudent(s))
                .thenReturn(List.of());

        assertThrows(IllegalStateException.class,
                () -> studentService.selectCourses("S1", List.of("C1")));
    }

    @Test
    void classYearNotEnough_throws() {
        Student s = new Student();
        s.setClassYear(1);

        Course c = new Course();
        c.setClassYear(3);

        when(studentRepository.findByStudentNumber("S1"))
                .thenReturn(Optional.of(s));
        when(courseRepository.findByCode("C1"))
                .thenReturn(Optional.of(c));

        assertThrows(IllegalStateException.class,
                () -> studentService.selectCourses("S1", List.of("C1")));
    }
}
