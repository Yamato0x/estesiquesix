-- 1. Crear la Base de Datos
-- NOTA: En Postgres, ejecuta esta línea SOLA primero, luego conéctate a la base de datos 'tienda_db'
-- CREATE DATABASE tienda_db;

-- -----------------------------------------------------
-- 2. Tabla USERS
-- "SERIAL" reemplaza a AUTO_INCREMENT
-- -----------------------------------------------------
CREATE TABLE users (
    userid SERIAL PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    rut VARCHAR(20) UNIQUE NOT NULL,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    rol VARCHAR(50) NOT NULL
);

-- -----------------------------------------------------
-- 3. Tabla PRODUCTOS
-- -----------------------------------------------------
CREATE TABLE productos (
    idproducto SERIAL PRIMARY KEY,
    nombreproducto VARCHAR(100) NOT NULL,
    precioproducto DECIMAL(10, 2) NOT NULL,
    categoria VARCHAR(50)
);

-- -----------------------------------------------------
-- 4. Tabla CARRITO
-- -----------------------------------------------------
CREATE TABLE carrito (
    idcarrito SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT DEFAULT 1,

    -- Definición de Claves Foráneas (Foreign Keys)
    CONSTRAINT fk_usuario
        FOREIGN KEY (usuario_id) 
        REFERENCES users(userid)
        ON DELETE CASCADE,

    CONSTRAINT fk_producto
        FOREIGN KEY (producto_id) 
        REFERENCES productos(idproducto)
        ON DELETE CASCADE
);

-- -----------------------------------------------------
-- 5. DATOS DE PRUEBA
-- -----------------------------------------------------

-- Insertar usuario
INSERT INTO users (nombre_completo, rut, nombre_usuario, contrasena, correo) 
VALUES ('Juan Perez', '12345678-9', 'juanp', '123456', 'juan@correo.com');

-- Insertar productos
INSERT INTO productos (nombreproducto, precioproducto, categoria) 
VALUES ('Laptop Gamer', 1500.00, 'Tecnologia'),
       ('Mouse RGB', 25.50, 'Accesorios');

-- Insertar carrito (Juan quiere 1 Laptop y 2 Mouses)
INSERT INTO carrito (usuario_id, producto_id, cantidad) VALUES (1, 1, 1);
INSERT INTO carrito (usuario_id, producto_id, cantidad) VALUES (1, 2, 2);