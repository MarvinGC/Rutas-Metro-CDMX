SELECT * FROM estacion 
WHERE 
id_estacion = 8 - 1 
OR
id_estacion = 8 + 1
AND
id_linea = 3;

SELECT id_estacion, id_linea FROM 
(SELECT * FROM estacion WHERE estacion = "Tacuba") t
NATURAL JOIN
linea;

/*Datos curioso*/
SELECT estacion, COUNT(*) 
FROM estacion 
GROUP BY estacion HAVING COUNT(*) > 1;


SELECT * FROM estacion a
JOIN
(SELECT estacion, COUNT(*) 
FROM estacion 
GROUP BY estacion HAVING COUNT(*) > 1) b
ON a.estacion = b.estacion;

SELECT id_linea, COUNT(*) 
FROM estacion 
GROUP BY id_linea HAVING COUNT(*) > 1;

