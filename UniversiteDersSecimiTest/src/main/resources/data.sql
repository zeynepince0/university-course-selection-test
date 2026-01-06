INSERT INTO students (student_number, name, class_year, max_credit)
VALUES ('202012345', 'Test Student', 3, 30);

INSERT INTO departments (code, name)
VALUES
    ('COMPUTER_ENGINEERING', 'Computer Engineering'),
    ('MATHEMATICS', 'Mathematics'),
    ('PHYSICS', 'Physics');

INSERT INTO courses (code, name, credit, quota, class_year, department_id)
VALUES
    ('CSE101', 'Intro to CS', 5, 30, 1, 1),
    ('MAT201', 'Math II', 5, 30, 2, 2),
    ('PHY105', 'Physics', 4, 30, 1, 3);
