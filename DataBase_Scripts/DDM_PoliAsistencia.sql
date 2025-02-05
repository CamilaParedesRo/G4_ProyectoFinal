-- database: ../Database/PoliAsistencia.sqlite
/* 
copyRigth Epn 2025
Autor: Anahi Pillajo, Camila Paredes, Eliana Pinargote, Adayeli 
Fecha: 20-01-2025
DDM--Data PARA MOSTRAR LA ASISTENCIA DEL SISTEMA. 
*/ 
/* 
copyRigth Epn 2025
Autor: Anahi Pillajo, Camila Paredes, Eliana Pinargote, Adayeli 
Fecha: 20-01-2025
DDM--Data PARA MOSTRAR LA ASISTENCIA DEL SISTEMA. 
*/ 
-- Eliminar vistas en el orden correcto
DROP VIEW IF EXISTS vista_historial_asistencia;
DROP VIEW IF EXISTS vista_estudiantes;
DROP VIEW IF EXISTS vista_profesores;

-- Vista de estudiantes
CREATE VIEW vista_estudiantes AS
SELECT
    e.id_estudiante AS ID_Estudiante,
    e.nombre_estudiante AS Nombre_Estudiante,
    e.apellido_estudiante AS Apellido_Estudiante,
    e.codigo_unico_estudiante AS Codigo_Unico_Estudiante,
    e.cedula_estudiante AS Cedula_Estudiante,
    e.correo_estudiante AS Correo_Estudiante,
    e.usuario_estudiante AS Usuario_Estudiante,
    e.fecha_registro AS Fecha_Registro,
    e.fecha_modifica AS Fecha_Modifica,
    e.estado AS Estado_Estudiante,
    s.nombre_sexo AS Sexo_Estudiante
FROM
    estudiante e
JOIN
    sexo s ON e.id_sexo = s.id_sexo;

-- Vista de profesores
CREATE VIEW vista_profesores AS
SELECT
    p.id_profesor AS ID_Profesor,
    p.nombre_profesor AS Nombre_Profesor,
    p.apellido_profesor AS Apellido_Profesor,
    p.cedula_profesor AS Cedula_Profesor,
    p.correo_profesor AS Correo_Profesor,
    p.usuario_profesor AS Usuario_Profesor,
    p.fecha_registro AS Fecha_Registro,
    p.fecha_modifica AS Fecha_Modifica,
    p.estado AS Estado_Profesor,
    s.nombre_sexo AS Sexo_Profesor
FROM
    profesor p
JOIN
    sexo s ON p.id_sexo = s.id_sexo;

-- Vista del historial de asistencia
CREATE VIEW vista_historial_asistencia AS
SELECT
    a.id_asistencia AS ID_Asistencia,
    e.nombre_estudiante AS Nombre_Estudiante,
    e.apellido_estudiante AS Apellido_Estudiante,
    e.codigo_unico_estudiante AS Codigo_Unico_Estudiante,
    a.fecha_asistencia AS Fecha_Asistencia,
    a.metodo_asistencia AS Metodo_Asistencia
FROM
    asistencia a
JOIN
    estudiante e ON a.id_estudiante = e.id_estudiante
ORDER BY
    a.fecha_asistencia DESC;

-- Insertar datos en la tabla sexo
INSERT INTO sexo (nombre_sexo) VALUES 
('Masculino'),
('Femenino');

-- Insertar profesores
INSERT INTO profesor (nombre_profesor, apellido_profesor, cedula_profesor, id_sexo, correo_profesor, usuario_profesor, clave_profesor)
VALUES 
('Ana', 'Gómez', '0987654321', 2, 'ana.gomez@dominio.com', 'ana123', '000'),
('Carlos', 'Pérez', '1234567890', 1, 'carlos.perez@dominio.com', 'carlos123', '111');

-- Insertar estudiantes
INSERT INTO estudiante (nombre_estudiante, apellido_estudiante, cedula_estudiante, codigo_unico_estudiante, id_sexo, correo_estudiante, usuario_estudiante, clave_estudiante)
VALUES 
('Juan', 'Pérez', '1122334455', '23454', 1, 'juan.perez@dominio.com', 'juan123', '123'),
('María', 'Rodríguez', '2233445566', '123456', 2, 'maria.rodriguez@dominio.com', 'maria123', '123');

-- Insertar asistencia
INSERT INTO asistencia (id_estudiante, fecha_asistencia, metodo_asistencia)
VALUES 
(1, datetime('now', 'localtime'), 'QR'),
(2, datetime('now', 'localtime'), 'Cédula');
    
    