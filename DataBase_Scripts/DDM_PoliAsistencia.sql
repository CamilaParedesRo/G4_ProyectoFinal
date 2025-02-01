-- database: ../Database/PoliAsistencia.sqlite
/* 
copyRigth Epn 2025
Autor: Anahi Pillajo, Camila Paredes, Eliana Pinargote, Adayeli 
Fecha: 20-01-2025
DDM--Data PARA MOSTRAR LA ASISTENCIA DEL SISTEMA. 
*/ 

DROP VIEW IF EXISTS vista_asistencia;

CREATE VIEW vista_asistencia AS
SELECT
    a.id_asistencia  AS ID_Asistencia
    ,e.nombre_estudiante AS Nombre_Estudiante
    ,e.apellido_estudiante AS Apellido_Estudiante
    ,e.cedula_estudiante AS Cedula_Estudiante
    ,a.fecha_asistencia  AS Fecha 
    ,a.metodo_asistencia AS Metodo
FROM
    asistencia a
JOIN
    estudiante e ON a.id_estudiante = e.id_estudiante;