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
    user_type enum('Client', 'Employee') not null
);

insert into words (word, hint1, hint2, hint3) values
('PROGRAMACION', 'Se refiere a escribir código', 'Requiere lógica', 'Es la base de software'),
('JAVASCRIPT', 'Lenguaje de scripting', 'Para desarrollo web', 'Permite interactividad'),
('HTML', 'Lenguaje de marcas', 'Estructura páginas web', 'No es un lenguaje de programación'),
('CSS', 'Hojas de estilo', 'Para dar diseño', 'Define apariencia visual'),
('PYTHON', 'Lenguaje versátil', 'Popular en ciencia de datos', 'Sintaxis clara'),
('VARIABLE', 'Contenedor de datos', 'Su valor puede cambiar', 'Se declara con un nombre'),
('FUNCION', 'Bloque de código', 'Realiza una tarea específica', 'Puede recibir parámetros'),
('BUCLE', 'Repetición de acciones', 'Hasta que se cumpla una condición', 'Ej: FOR, WHILE'),
('OBJETO', 'Estructura de datos', 'Propiedades y métodos', 'Representa entidades'),
('CLASE', 'Plantilla para objetos', 'Define estructura y comportamiento', 'Programación orientada a objetos'),
('CADENA', 'Secuencia de caracteres', 'Texto', 'Representada entre comillas'),
('BOOLEANO', 'Tipo de dato', 'Verdadero o falso', 'Lógica'),
('METODO', 'Función asociada a un objeto', 'Realiza una acción', 'Ej: array.push()');


-- ----------------- Insert sample users -----------------
insert into users (username, email, password, user_type) values
('Jeremy', 'J', 'J', 'employee');

select * from words;
select * from users;
