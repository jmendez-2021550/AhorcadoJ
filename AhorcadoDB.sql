Drop database if exists AhorcadoDB;
Create database AhorcadoDB;
Use AhorcadoDB;

Create table words (
    id int auto_increment primary key,
    word varchar(50) not null,
    hint1 varchar(100) not null,
    hint2 varchar(100),
    hint3 varchar(100)
);

Create table users (
    user_id int auto_increment primary key,
    username varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(255) not null,
    user_type Varchar(50) not null
);

insert into words (word, hint1, hint2, hint3) values
('PROGRAMACION', 'Se refiere a escribir código', 'Requiere lógica', 'Es la base de software');



-- ----------------- Insert sample users -----------------
insert into users (username, email, password, user_type) values
('Jeremy', 'J', 'J', 'employee');

select * from words;
select * from users;
