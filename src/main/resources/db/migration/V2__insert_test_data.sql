INSERT INTO users (id, email, firstname, lastname, password, registration_date, role)
VALUES
    (1, 'yurii1@gmail.com', 'yurii', 'yurii', '$2a$10$t5Oe5eckAVHwQlmO7F4ypO2fkhAJjM.E49/eFIVoW0k/fN69TUiR2', '2023-01-01', 0),
    (2, 'yurii2@gmail.com', 'yurii', 'yurii', '$2a$10$t5Oe5eckAVHwQlmO7F4ypO2fkhAJjM.E49/eFIVoW0k/fN69TUiR2', '2023-01-01', 1),
    (3, 'yurii3@gmail.com', 'yurii', 'yurii', '$2a$10$t5Oe5eckAVHwQlmO7F4ypO2fkhAJjM.E49/eFIVoW0k/fN69TUiR2', '2023-01-01', 2),
    (4, 'yurii4@gmail.com', 'yurii', 'yurii', '$2a$10$t5Oe5eckAVHwQlmO7F4ypO2fkhAJjM.E49/eFIVoW0k/fN69TUiR2', '2023-01-01', 0),
    (5, 'yurii5@gmail.com', 'yurii', 'yurii', '$2a$10$t5Oe5eckAVHwQlmO7F4ypO2fkhAJjM.E49/eFIVoW0k/fN69TUiR2', '2023-01-01', 1),
    (6, 'yurii6@gmail.com', 'yurii', 'yurii', '$2a$10$t5Oe5eckAVHwQlmO7F4ypO2fkhAJjM.E49/eFIVoW0k/fN69TUiR2', '2023-01-01', 2);

INSERT INTO courses (id, end_date, start_date, title)
VALUES
    (1, '2023-06-01', '2023-04-01', 'Computer Science Basics'),
    (2, '2023-07-01', '2023-05-01', 'Design Patterns'),
    (3, '2023-08-01', '2023-06-01', 'DB'),
    (4, '2023-09-01', '2023-07-01', 'CyberSecurity'),
    (5, '2023-10-01', '2023-08-01', 'Computer Networks'),
    (6, '2023-11-01', '2023-09-01', 'Algorithms');

INSERT INTO person_course (course_id, person_id)
VALUES
    (1, 1),
    (2, 4),
    (3, 1),
    (4, 4),
    (3, 2),
    (4, 5),
    (3, 5),
    (5, 2),
    (2, 5),
    (1, 2),
    (6, 5);

INSERT INTO course_graduations (id, final_mark, status, course_id, student_id)
VALUES
    (1, 4, 0, 1, 1),
    (2, 3, 1, 2, 4),
    (3, 5, 0, 3, 1),
    (4, 4, 0, 3, 4);

INSERT INTO feedbacks (id, date, feedback_message, course_graduation_id, instructor_id)
VALUES
    (1, '2023-05-02', 'Feedback message 1', 1, 2),
    (2, '2023-06-02', 'Feedback message 2', 2, 5),
    (3, '2023-07-02', 'Feedback message 3', 3, 2),
    (4, '2023-08-02', 'Feedback message 4', 4, 5);

INSERT INTO lessons (id, title, course_id)
VALUES
    (1, 'Lesson1', 1),
    (2, 'Lesson2', 1),
    (3, 'Lesson3', 1),
    (4, 'Lesson4', 1),
    (5, 'Lesson5', 1),

    (6, 'Lesson1', 2),
    (7, 'Lesson2', 2),
    (8, 'Lesson3', 2),
    (9, 'Lesson4', 2),
    (10, 'Lesson5', 2),

    (11, 'Lesson1', 3),
    (12, 'Lesson2', 3),
    (13, 'Lesson3', 3),
    (14, 'Lesson4', 3),
    (15, 'Lesson5', 3),

    (16, 'Lesson1', 4),
    (17, 'Lesson2', 4),
    (18, 'Lesson3', 4),
    (19, 'Lesson4', 4),
    (20, 'Lesson5', 4),

    (21, 'Lesson1', 5),
    (22, 'Lesson2', 5),
    (23, 'Lesson3', 5),
    (24, 'Lesson4', 5),
    (25, 'Lesson5', 5),

    (26, 'Lesson1', 6),
    (27, 'Lesson2', 6),
    (28, 'Lesson3', 6),
    (29, 'Lesson4', 6),
    (30, 'Lesson5', 6);

INSERT INTO files (id, file, file_name, file_type)
VALUES
    ('f6a16ff7-4a31-11eb-be7b-8344edc8f33b', null, 'file1.png', 'image/png'),
    ('f6a16ff7-4a31-11eb-be7b-8344edc8f34b', null, 'file2.png', 'image/png'),
    ('f6a16ff7-4a31-11eb-be7b-8344edc8f36b', null, 'file3.png', 'image/png');

INSERT INTO homeworks (id, upload_date, file_id, lesson_id, student_id)
VALUES
    (1, '2023-05-02', 'f6a16ff7-4a31-11eb-be7b-8344edc8f33b', 1, 1),
    (2, '2023-05-02', 'f6a16ff7-4a31-11eb-be7b-8344edc8f34b', 6, 4),
    (3, '2023-05-02', 'f6a16ff7-4a31-11eb-be7b-8344edc8f36b', 11, 1);

INSERT INTO marks (id, date, mark, instructor_id, lesson_id, user_id)
VALUES
    (1, '2023-05-02', 4, 2, 1, 1),
    (2, '2023-05-06', 3, 5, 2, 4),
    (3, '2023-05-08', 5, 2, 3, 1),
    (4, '2023-05-10', 4, 5, 4, 4);


