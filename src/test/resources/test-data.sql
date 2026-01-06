-- src/test/resources/test-data.sql İÇERİĞİ:
DELETE FROM enrollments;
DELETE FROM courses;
DELETE FROM students;
DELETE FROM semesters;
DELETE FROM departments;

INSERT INTO departments (id, code, name) VALUES (1, 'ENG', 'Engineering');
INSERT INTO semesters (id, name) VALUES (1, '2025-1');
INSERT INTO students (id, student_number, name, class_year, max_credit) VALUES (1, '202012345', 'Selenium Student', 1, 30);
INSERT INTO courses (id, code, name, credit, quota, class_year, department_id) VALUES (1, 'CSE101', 'Computer Science 1', 5, 50, 1, 1);
INSERT INTO courses (id, code, name, credit, quota, class_year, department_id) VALUES (2, 'CSE102', 'Computer Science 2', 5, 50, 1, 1);
INSERT INTO enrollments (id, student_id, course_id, semester_id, status, created_at) VALUES (1, 1, 1, 1, 'PENDING', CURRENT_TIMESTAMP);

ALTER TABLE enrollments ALTER COLUMN id RESTART WITH 100;
ALTER TABLE courses ALTER COLUMN id RESTART WITH 100;
ALTER TABLE students ALTER COLUMN id RESTART WITH 100;
ALTER TABLE semesters ALTER COLUMN id RESTART WITH 100;
ALTER TABLE departments ALTER COLUMN id RESTART WITH 100;