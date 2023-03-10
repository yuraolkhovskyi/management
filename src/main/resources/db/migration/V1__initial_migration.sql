CREATE TABLE users
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY UNIQUE NOT NULL,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    registration_date TIMESTAMP NULL,
    role INT NOT NULL
);


CREATE TABLE courses
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY UNIQUE NOT NULL,
    title VARCHAR(100),
    start_date TIMESTAMP,
    end_date TIMESTAMP
);


CREATE TABLE person_course
(
    person_id INT NOT NULL,
    course_id INT NOT NULL,

    FOREIGN KEY (person_id) REFERENCES users (id),
    FOREIGN KEY (course_id) REFERENCES courses (id)
);


CREATE TABLE course_graduations
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY UNIQUE NOT NULL,
    final_mark INT NOT NULL,
    status INT NOT NULL,
    student_id INT NOT NULL,
    course_id INT NOT NULL,

    FOREIGN KEY (student_id) REFERENCES users (id),
    FOREIGN KEY (course_id) REFERENCES courses (id)
);

CREATE TABLE feedbacks
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY UNIQUE NOT NULL,
    feedback_message VARCHAR(255),
    date TIMESTAMP,
    course_graduation_id INT NOT NULL,
    instructor_id INT NOT NULL,

    FOREIGN KEY (course_graduation_id) REFERENCES course_graduations (id),
    FOREIGN KEY (instructor_id) REFERENCES users (id)
);

CREATE TABLE lessons
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY UNIQUE NOT NULL,
    title VARCHAR(255),
    course_id INT NOT NULL,

    FOREIGN KEY (course_id) REFERENCES courses (id)
);


CREATE TABLE files
(
    id UUID NOT NULL UNIQUE PRIMARY KEY,
    file_name VARCHAR(100),
    file_type VARCHAR(100),
    file BYTEA
);


CREATE TABLE homeworks
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY UNIQUE NOT NULL,
    upload_date TIMESTAMP,
    lesson_id INT NOT NULL,
    student_id INT NOT NULL,
    file_id UUID NOT NULL,

    FOREIGN KEY (lesson_id) REFERENCES lessons (id),
    FOREIGN KEY (student_id) REFERENCES users (id),
    FOREIGN KEY (file_id) REFERENCES files (id)
);

CREATE TABLE marks
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY UNIQUE NOT NULL,
    mark INT NOT NULL,
    date TIMESTAMP,
    lesson_id INT NOT NULL,
    user_id INT NOT NULL,
    instructor_id INT NOT NULL,

    FOREIGN KEY (lesson_id) REFERENCES lessons (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (instructor_id) REFERENCES users (id)
);