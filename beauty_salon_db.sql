-- PostgreSQL
CREATE DATABASE beauty_salon_db;
-- USE beauty_salon_db;

CREATE DATABASE beauty_salon_db;

CREATE TABLE usr (
    id SERIAL PRIMARY KEY,
    name varchar(32) NOT NULL,
    email varchar(128) NOT NULL unique,
    password varchar(64) not null,
    role_id INT NOT NULL DEFAULT 2, -- 1 - ADMIN, 2 - CUSTOMER, 3 - HAIRDRESSER
    rating INT NOT NULL DEFAULT 0
);
INSERT INTO usr (name, email, password, role_id, rating)
VALUES ('admin', 'admin@mail.com', '2', 1, 0);
INSERT INTO usr (name, email, password, role_id, rating)
VALUES ('Hairdresser', 'hairdresser@mail.com', '3', 3, 0);
INSERT INTO usr (name, email, password, role_id, rating)
VALUES ('Customer 1', 'customer@mail.com', '4', 2, 0);
SELECT * FROM usr;

CREATE TABLE service (
    id SERIAL PRIMARY KEY,
    name varchar(32) NOT NULL unique,
    price INT NOT NULL DEFAULT 0
);
INSERT INTO service (name, price)
VALUES ('Hair cut', 200);
INSERT INTO service (name, price)
VALUES ('Hair style', 300);
INSERT INTO service (name, price)
VALUES ('Hair coloring', 450);
SELECT * FROM service;

CREATE TABLE service_hairdresser(
    id SERIAL PRIMARY KEY,
    service_id int REFERENCES service(id) ON DELETE CASCADE,
    hairdresser_id int REFERENCES usr(id) ON DELETE CASCADE
);
SELECT * FROM service_hairdresser;
INSERT INTO service_hairdresser(service_id, hairdresser_id)
VALUES (1, 2);
INSERT INTO service_hairdresser(service_id, hairdresser_id)
VALUES (2, 2);

CREATE TABLE request (
    id SERIAL PRIMARY KEY,
    servicehairdresser_id int REFERENCES service_hairdresser(id) ON DELETE CASCADE,
    client_id int REFERENCES usr(id) ON DELETE CASCADE,
    status varchar(10) NOT NULL DEFAULT 'ACTIVE',
    date timestamp NOT NULL DEFAULT now()
);
INSERT INTO request(servicehairdresser_id, client_id)
VALUES(1, 3);
SELECT * FROM request;

CREATE TABLE feedback (
    id SERIAL PRIMARY KEY,
    request_id int REFERENCES request(id) ON DELETE CASCADE,
    name VARCHAR(256) NOT NULL,
    rate INT NOT NULL default 4,
    created timestamp NOT NULL DEFAULT now()
);
SELECT * FROM feedback;

ALTER TABLE usr ALTER COLUMN rating type decimal(10,2);
ALTER TABLE request ADD paid bool default FALSE;
