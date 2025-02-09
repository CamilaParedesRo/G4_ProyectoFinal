-- database: ../Database/PoliAsistencia.sqlite
/* 
copyRigth Epn 2025
Autor: Anahi Pillajo, Camila Paredes, Eliana Pinargote, Adayeli 
Fecha: 20-01-2025
DDL--Data base para crear las bases principales del sistema PoliAsistencia. 
*/ 
-- 1. Deshabilitar claves foráneas temporalmente
PRAGMA foreign_keys = OFF;
-- 2. Eliminar tablas en el orden correcto
DROP TABLE IF EXISTS asistencia;
DROP TABLE IF EXISTS QRS;
DROP TABLE IF EXISTS estudiante;
DROP TABLE IF EXISTS profesor;
DROP TABLE IF EXISTS sexo;

-- 3. Crear tabla sexo
CREATE TABLE sexo (
    id_sexo INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre_sexo VARCHAR(50) NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    fecha_modifica DATETIME,
    estado CHAR(1) NOT NULL DEFAULT 'A'
);

-- 4. Crear tabla profesor
CREATE TABLE profesor (
    id_profesor INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre_profesor VARCHAR(50) NOT NULL,
    apellido_profesor VARCHAR(50) NOT NULL,
    cedula_profesor VARCHAR(10) UNIQUE NOT NULL,
    id_sexo INTEGER NOT NULL REFERENCES sexo(id_sexo) ON DELETE CASCADE,
    correo_profesor VARCHAR(50) UNIQUE NOT NULL,
    usuario_profesor VARCHAR(225) UNIQUE NOT NULL,
    clave_profesor VARCHAR(225) NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    fecha_modifica DATETIME,
    estado CHAR(1) NOT NULL DEFAULT 'A'
);

-- 5. Crear tabla estudiante
CREATE TABLE estudiante (
    id_estudiante INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre_estudiante VARCHAR(50) NOT NULL,
    apellido_estudiante VARCHAR(50) NOT NULL,
    cedula_estudiante VARCHAR(10) UNIQUE NOT NULL,
    codigo_unico_estudiante VARCHAR(10) UNIQUE NOT NULL,
    id_sexo INTEGER NOT NULL REFERENCES sexo(id_sexo) ON DELETE CASCADE,
    correo_estudiante VARCHAR(50) UNIQUE NOT NULL,
    usuario_estudiante VARCHAR(50) UNIQUE NOT NULL,
    clave_estudiante VARCHAR(225) NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    fecha_modifica DATETIME,
    estado CHAR(1) NOT NULL DEFAULT 'A'
);
CREATE TABLE asistencia (
    id_asistencia INTEGER PRIMARY KEY AUTOINCREMENT,
    id_estudiante INTEGER NOT NULL REFERENCES estudiante(id_estudiante) ON DELETE CASCADE,
    fecha_asistencia DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    metodo_asistencia VARCHAR(50) NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    fecha_modifica DATETIME,
    estado CHAR(1) NOT NULL DEFAULT 'A',
    UNIQUE (id_estudiante, fecha_asistencia)
);

-- 7. Crear tabla QRS
CREATE TABLE QRS (
    id_QR INTEGER PRIMARY KEY AUTOINCREMENT,
    QR VARCHAR(225) UNIQUE NOT NULL,
    id_estudiante INTEGER NOT NULL REFERENCES estudiante(id_estudiante) ON DELETE CASCADE,
    fecha_registro DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    fecha_modifica DATETIME,
    estado CHAR(1) NOT NULL DEFAULT 'A'
);

-- 8. Reactivar las restricciones de claves foráneas
PRAGMA foreign_keys = ON;