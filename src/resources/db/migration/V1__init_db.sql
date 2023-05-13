CREATE TYPE university_web_app_user_role AS ENUM ('ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_STUDENT');

CREATE TABLE users
(
    user_id bigserial PRIMARY KEY,
    email varchar(256) UNIQUE NOT NULL,
    password varchar(200) NOT NULL,
    first_name varchar(15) DEFAULT NULL,
    last_name varchar(30) DEFAULT NULL,
    role university_web_app_user_role NOT NULL DEFAULT 'ROLE_STUDENT',
    phone varchar(15) UNIQUE DEFAULT NULL
);

INSERT INTO users
VALUES (1, 'admin@university.com', '$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m', 'John', 'Smith', 'ROLE_ADMIN', '138765487'),
       (2, 'teacher1@university.com', '$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m', 'Tom', 'Green', 'ROLE_TEACHER', '135481314'),
       (3, 'teacher2@university.com', '$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m', 'Anthony', 'Newman', 'ROLE_TEACHER', '846321871'),
       (4, 'student1@university.com', '$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m', 'Kimberly', 'Foster', 'ROLE_STUDENT', '9318431812'),
       (5, 'student2@university.com', '$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m', 'Daniel', 'Jones', 'ROLE_STUDENT', '7313544342'),
       (6, 'student3@university.com', '$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m', 'William', 'Brown', 'ROLE_STUDENT', '3246841321'),
       (7, 'student4@university.com', '$2a$10$kTtZAqCVvwUk0V3q3igTj.isbnf9xhGNIY/Tnn6TPJZYmlSc4lo.m', 'Edward', 'Taylor', 'ROLE_STUDENT', '7654348946');

SELECT setval('users_user_id_seq', (SELECT MAX(user_id) from "users"));

CREATE TABLE courses
(
    course_id bigserial PRIMARY KEY,
    course_name varchar(15) NOT NULL,
    description varchar(100) DEFAULT NULL
);

INSERT INTO courses
VALUES (1, 'Math', 'Math lessons'),
       (2, 'Biology', 'Biology lessons');
SELECT setval('courses_course_id_seq', (SELECT MAX(course_id) from "courses"));

CREATE TABLE student_course
(
    course_id bigint REFERENCES courses(course_id) ON DELETE CASCADE,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (course_id, user_id)
);

INSERT INTO student_course
VALUES (1, 4),
       (1, 5),
       (1, 6),
       (2, 5),
       (2, 6),
       (2, 7);

CREATE TABLE teacher_course
(
    course_id bigint REFERENCES courses(course_id) ON DELETE CASCADE,
    user_id bigint REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (course_id, user_id)
);

INSERT INTO teacher_course
VALUES (1, 2),
       (2, 3);

CREATE TABLE schedules
(
    schedule_id bigserial PRIMARY KEY,
    course_id bigint REFERENCES courses(course_id)  ON DELETE CASCADE,
    start_time timestamp NOT NULL,
    end_time timestamp NOT NULL
);

INSERT INTO schedules
VALUES (1, 1, '2023-03-23 09:00:00', '2023-03-23 09:45:00'),
       (2, 2, '2023-03-23 10:00:00', '2023-03-23 10:45:00'),
       (3, 1, '2023-03-24 09:00:00', '2023-03-24 09:45:00'),
       (4, 2, '2023-03-24 10:00:00', '2023-03-24 10:45:00'),
       (5, 1, '2023-03-26 09:00:00', '2023-03-26 09:45:00'),
       (6, 2, '2023-03-26 10:00:00', '2023-03-26 10:45:00'),
       (7, 2, '2023-04-01 10:00:00', '2023-04-01 10:45:00');

SELECT setval('schedules_schedule_id_seq', (SELECT MAX(schedule_id) from "schedules"));
