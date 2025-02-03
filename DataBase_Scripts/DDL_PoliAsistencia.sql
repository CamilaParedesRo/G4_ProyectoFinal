-- database: ../Database/PoliAsistencia.sqlite
/* 
copyRigth Epn 2025
Autor: Anahi Pillajo, Camila Paredes, Eliana Pinargote, Adayeli 
Fecha: 20-01-2025
DDL--Data base para crear las bases principales del sistema PoliAsistencia. 
*/ 
DROP TABLE IF EXISTS asistencia;
DROP TABLE IF EXISTS profesor;
DROP TABLE IF EXISTS estudiante;


CREATE TABLE estudiante (
    id_estudiante INTEGER PRIMARY KEY AUTOINCREMENT
    ,nombre_estudiante VARCHAR(50) NOT NULL
    ,apellido_estudiante VARCHAR(50) NOT NULL
    ,cedula_estudiante VARCHAR(10) UNIQUE NOT NULL
    ,código_unico_estudiante VARCHAR(10) UNIQUE NOT NULL
    ,usuario_estudiante VARCHAR(50) UNIQUE NOT NULL
    ,clave_estudiante VARCHAR(225) NOT NULL
    ,QR_estudiante VARCHAR(225) UNIQUE NOT NULL
    ,fecha_registro  DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
    ,fecha_modifica DATETIME 
    ,estado CHAR(1) NOT NULL DEFAULT 'A'
);

CREATE TABLE profesor (
    id_profesor INTEGER PRIMARY KEY AUTOINCREMENT
    ,nombre_profesor VARCHAR(50) NOT NULL
    ,apellido_profesor VARCHAR(50) NOT NULL
    ,cedula_profesor VARCHAR(10) UNIQUE NOT NULL
    ,correo_profesor VARCHAR(50) UNIQUE NOT NULL
    ,usuario_profesor VARCHAR(225) UNIQUE NOT NULL
    ,clave_profesor VARCHAR(225) NOT NULL
    ,fecha_registro  DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
    ,fecha_modifica DATETIME 
    ,estado CHAR(1) NOT NULL DEFAULT 'A'
);

CREATE TABLE asistencia(
    id_asistencia INTEGER PRIMARY KEY AUTOINCREMENT
    ,id_estudiante INTEGER NOT NULL  REFERENCES estudiante(id_estudiante)
    ,fecha_asistencia DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
    ,metodo_asistencia VARCHAR(50) NOT NULL
    ,fecha_registro  DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
    ,fecha_modifica DATETIME 
    ,estado CHAR(1) NOT NULL DEFAULT 'A'
);



