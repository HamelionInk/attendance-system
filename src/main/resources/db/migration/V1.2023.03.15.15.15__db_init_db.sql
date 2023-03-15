create sequence seq_admin start 1 increment 1;
create sequence seq_attendances start 1 increment 1;
create sequence seq_coaches start 1 increment 1;
create sequence seq_groups start 1 increment 1;
create sequence seq_lessons start 1 increment 1;
create sequence seq_persons start 1 increment 1;
create sequence seq_students start 1 increment 1;

create table persons (
                         id INT8 NOT NULL,
                         password VARCHAR(255),
                         first_name VARCHAR(255),
                         last_name VARCHAR(255),
                         patronymic VARCHAR(255),
                         number_phone VARCHAR(255),
                         age INT4,
                         email VARCHAR(255),
                         type_user VARCHAR(255),
                         PRIMARY KEY (id)
);

create table administrations (
    id INT8 NOT NULL,
    person_id INT8 NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES persons(id)
);

create table coaches (
                         id INT8 NOT NULL,
                         person_id INT8 NOT NULL,
                         PRIMARY KEY (id),
                         FOREIGN KEY (person_id) REFERENCES persons(id)
);

create table groups (
                        id INT8 NOT NULL,
                        group_name VARCHAR(255),
                        max_age INT4,
                        min_AGE INT4,
                        number_of_students INT4,
                        PRIMARY KEY (id)
);

create table students (
                          id INT8 NOT NULL,
                          person_id INT8 NOT NULL,
                          group_id INT8,
                          PRIMARY KEY (id),
                          FOREIGN KEY (person_id) REFERENCES persons(id),
                          FOREIGN KEY (group_id) REFERENCES groups(id)
);

create table lessons (
                         id INT8 NOT NULL,
                         start_date TIMESTAMP,
                         end_date TIMESTAMP,
                         lesson_name VARCHAR(255),
                         coach_name VARCHAR(255),
                         coach_id INT8 NOT NULL,
                         group_id INT8 NOT NULL,
                         lesson_finish BOOLEAN,
                         PRIMARY KEY (id),
                         FOREIGN KEY (coach_id) REFERENCES coaches(id),
                         FOREIGN KEY (group_id) REFERENCES groups(id)
);

create table attendances (
    id INT8 NOT NULL,
    student_name VARCHAR,
    attendance BOOLEAN,
    lesson_id INT8,
    student_id INT8,
    PRIMARY KEY (id),
    FOREIGN KEY (lesson_id) REFERENCES lessons(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

