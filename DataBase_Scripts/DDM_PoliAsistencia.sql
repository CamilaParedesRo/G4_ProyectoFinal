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

-- Insertar asistencia
INSERT INTO asistencia (id_estudiante, fecha_asistencia, metodo_asistencia)
VALUES 
(1, datetime('now', 'localtime'), 'QR'),
(2, datetime('now', 'localtime'), 'Cédula');

INSERT INTO estudiante (nombre_estudiante, apellido_estudiante, cedula_estudiante, codigo_unico_estudiante, id_sexo, correo_estudiante, usuario_estudiante, clave_estudiante)
VALUES 
('Juan', 'Pérez', '1122334455', '2345400001', 1, 'juan.perez@epn.edu.ec', 'juan123', 'clave1234'),
('María', 'Rodríguez', '2233445566', '1234567890', 2, 'maria.rodriguez@epn.edu.ec', 'maria123', 'clave5678'),
('Carlos', 'Gómez', '3344556677', '3456700002', 1, 'carlos.gomez@epn.edu.ec', 'carlos123', 'clave9012'),
('Ana', 'López', '4455667788', '4567800003', 2, 'ana.lopez@epn.edu.ec', 'ana123', 'clave3456'),
('Luis', 'Martínez', '5566778899', '5678900004', 1, 'luis.martinez@epn.edu.ec', 'luis123', 'clave7890'),
('Elena', 'Santos', '6677889900', '6789100005', 2, 'elena.santos@epn.edu.ec', 'elena123', 'clave1234'),
('Jorge', 'Hernández', '7788990011', '7890100006', 1, 'jorge.hernandez@epn.edu.ec', 'jorge123', 'clave5678'),
('Valeria', 'Castro', '8899001122', '8901200007', 2, 'valeria.castro@epn.edu.ec', 'valeria123', 'clave9012'),
('Pablo', 'Ramírez', '9900112233', '9012300008', 1, 'pablo.ramirez@epn.edu.ec', 'pablo123', 'clave3456'),
('Lucía', 'Ortega', '1111223344', '0123400009', 2, 'lucia.ortega@epn.edu.ec', 'lucia123', 'clave7890'),
('Diego', 'Vega', '2222334455', '1234500010', 1, 'diego.vega@epn.edu.ec', 'diego123', 'clave1234'),
('Camila', 'Flores', '3333445566', '2345600011', 2, 'camila.flores@epn.edu.ec', 'camila123', 'clave5678'),
('Fernando', 'Cruz', '4444556677', '3456700012', 1, 'fernando.cruz@epn.edu.ec', 'fernando123', 'clave9012'),
('Isabel', 'Mendoza', '5555667788', '4567800013', 2, 'isabel.mendoza@epn.edu.ec', 'isabel123', 'clave3456'),
('Andrés', 'Suárez', '6666778899', '5678900014', 1, 'andres.suarez@epn.edu.ec', 'andres123', 'clave7890'),
('Sofía', 'Vargas', '7777889900', '6789100015', 2, 'sofia.vargas@epn.edu.ec', 'sofia123', 'clave1234'),
('Ricardo', 'Romero', '8888990011', '7890100016', 1, 'ricardo.romero@epn.edu.ec', 'ricardo123', 'clave5678'),
('Daniela', 'Reyes', '9999001122', '8901200017', 2, 'daniela.reyes@epn.edu.ec', 'daniela123', 'clave9012'),
('Héctor', 'Aguilar', '1111222233', '9012300018', 1, 'hector.aguilar@epn.edu.ec', 'hector123', 'clave3456'),
('Mónica', 'Espinoza', '2222333344', '0123400019', 2, 'monica.espinoza@epn.edu.ec', 'monica123', 'clave7890');

INSERT INTO asistencia (id_estudiante, fecha_asistencia, metodo_asistencia)
VALUES 
    (1, '2025-01-01 08:30:00', 'Manual'),
    (2, '2025-01-02 09:15:00', 'QR'),
    (3, '2025-01-03 10:00:00', 'Manual'),
    (4, '2025-01-04 08:45:00', 'QR'),
    (5, '2025-01-05 09:30:00', 'Manual'),
    (6, '2025-01-06 10:15:00', 'QR'),
    (7, '2025-01-07 08:00:00', 'Manual'),
    (8, '2025-01-08 09:45:00', 'QR'),
    (9, '2025-01-09 10:30:00', 'Manual'),
    (10, '2025-01-10 08:15:00', 'QR'),
    (11, '2025-01-11 09:00:00', 'Manual'),
    (12, '2025-01-12 10:30:00', 'QR'),
    (13, '2025-01-13 08:20:00', 'Manual'),
    (14, '2025-01-14 09:50:00', 'QR'),
    (15, '2025-01-15 10:40:00', 'Manual'),
    (16, '2025-01-16 08:10:00', 'QR'),
    (17, '2025-01-17 09:35:00', 'Manual'),
    (18, '2025-01-18 10:20:00', 'QR'),
    (19, '2025-01-19 08:50:00', 'Manual'),
    (20, '2025-01-20 09:25:00', 'QR');
    
