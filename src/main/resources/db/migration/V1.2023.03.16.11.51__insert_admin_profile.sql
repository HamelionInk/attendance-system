INSERT INTO persons (id, age, email, first_name, last_name, number_phone, password, patronymic, type_user)
VALUES (1, 24, 'adminemail@gmail.com', 'Admin', 'Admin', '89085328288', 'admin', 'admin', 'ADMIN');
INSERT INTO administrations (id, person_id) SELECT 1, persons.id from persons WHERE number_phone='89085328288';