-- Database: "AirServiceDb"
CREATE TABLE public.airports
(
    id       bigserial    NOT NULL,
    "name"   varchar(255) NOT NULL,
    capacity int4         NOT NULL,
    CONSTRAINT airport_pk PRIMARY KEY (id)
);

CREATE TABLE public.planes
(
    id         bigserial    NOT NULL,
    "name"     varchar(255) NOT NULL,
    airport_id int8         NULL,
    status     varchar(255) NOT NULL,
    CONSTRAINT plane_pk PRIMARY KEY (id),
    CONSTRAINT plane_fk_port FOREIGN KEY (airport_id) REFERENCES airports (id)
);

INSERT INTO public.airports ("name", capacity)
VALUES ('Шереметьево', 5),
       ('Гагарин', 10),
       ('Восточный', 7);

INSERT INTO public.planes ("name", airport_id, status)
VALUES ('Летун', 1, 'AIRPORT'),
       ('Боинг-123', 1, 'AIRPORT'),
       ('АЭРОФЛОТ-7733', 1, 'AIRPORT'),
       ('Буря', 1, 'AIRPORT'),
       ('ЯК-42', 1, 'AIRPORT'),
       ('АН-2', 2, 'AIRPORT'),
       ('МИГ-31', 2, 'AIRPORT'),
       ('Тёркиш Эйрлайнс', 3, 'AIRPORT'),
       ('Арктика', NULL, 'AIR'),
       ('Моккарто', 3, 'AIRPORT');

