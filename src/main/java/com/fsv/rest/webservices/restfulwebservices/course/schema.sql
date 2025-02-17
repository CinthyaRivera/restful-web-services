--Mover esta tabla a properties cuando se quiera ejecutar course
create table course
(   id bigint not null,
    name varchar(255) not null,
    author varchar(255) not null,
    primary key (id)
);

INSERT INTO COURSE
VALUES (1,'Learn AWS','Cinthya Rivera');