Drop database if exists AhorcadoDB;
Create database AhorcadoDB;
Use AhorcadoDB;

-- ----------------- Tabla de Palabras y Pistas -----------------
Create table Palabras (
    id int auto_increment primary key,
    palabra varchar(50) not null,
    pista1 varchar(100) not null,
    pista2 varchar(100),
    pista3 varchar(100)
);

-- ----------------- Tabla de Usuarios -----------------
Create table Usuarios (
    codigoUsuario int auto_increment primary key,
    nombreUsuario varchar(100) not null,
    correo varchar(100) not null unique,
    contrasena varchar(255) not null,
    tipoUsuario ENUM('Cliente', 'Empleado') not null
);

-- ----------------- Insertar palabras para el juego -----------------
Insert into Palabras (palabra, pista1, pista2, pista3) values
('PROGRAMACION', 'Se refiere a escribir código', 'Requiere lógica', 'Es la base de software'),
('JAVASCRIPT', 'Lenguaje de scripting', 'Para desarrollo web', 'Permite interactividad'),
('HTML', 'Lenguaje de marcas', 'Estructura páginas web', 'No es un lenguaje de programación'),
('CSS', 'Hojas de estilo', 'Para dar diseño', 'Define apariencia visual'),
('PYTHON', 'Lenguaje versátil', 'Popular en ciencia de datos', 'Sintaxis clara'),
('NODEJS', 'Entorno de ejecución', 'Para backend JavaScript', 'Permite crear servidores'),
('REACT', 'Librería de JavaScript', 'Para interfaces de usuario', 'Basada en componentes'),
('ANGULAR', 'Framework de JavaScript', 'Desarrollo de aplicaciones web', 'Desarrollado por Google'),
('VUEJS', 'Framework progresivo', 'Fácil de integrar', 'Buen rendimiento'),
('DATABASE', 'Almacén de datos', 'Organización de información', 'Puede ser relacional o NoSQL'),
('SERVIDOR', 'Computadora central', 'Proporciona servicios', 'Aloja aplicaciones web'),
('CLIENTE', 'Dispositivo que solicita', 'Navegador web', 'Interactúa con el servidor'),
('ALGORITMO', 'Conjunto de pasos', 'Para resolver problemas', 'Secuencia lógica'),
('VARIABLE', 'Contenedor de datos', 'Su valor puede cambiar', 'Se declara con un nombre'),
('FUNCION', 'Bloque de código', 'Realiza una tarea específica', 'Puede recibir parámetros'),
('BUCLE', 'Repetición de acciones', 'Hasta que se cumpla una condición', 'Ej: FOR, WHILE'),
('CONDICION', 'Evalúa una expresión', 'Verdadero o falso', 'Ej: IF, ELSE'),
('OBJETO', 'Estructura de datos', 'Propiedades y métodos', 'Representa entidades'),
('CLASE', 'Plantilla para objetos', 'Define estructura y comportamiento', 'Programación orientada a objetos'),
('ARRAY', 'Colección de elementos', 'Mismo tipo de datos', 'Acceso por índice'),
('CADENA', 'Secuencia de caracteres', 'Texto', 'Representada entre comillas'),
('BOOLEANO', 'Tipo de dato', 'Verdadero o falso', 'Lógica'),
('METODO', 'Función asociada a un objeto', 'Realiza una acción', 'Ej: array.push()'),
('EVENTO', 'Ocurrencia en el sistema', 'Acción del usuario', 'Clic, presionar tecla'),
('ASINCRONO', 'No bloquea la ejecución', 'Se ejecuta en segundo plano', 'Callbacks, Promises, Async/Await');

-- ----------------- Insertar usuarios de ejemplo -----------------
Insert into Usuarios (nombreUsuario, correo, contrasena, tipoUsuario) values
('Jeremy', 'J', 'J', 'Empleado'),
('Jugador Uno', '1', '1', 'Cliente');
