/*
        Natural join se usa cuando los campos por los cuales se enlazan las tablas tienen el mismo nombre
        Esquema anterior
        SELECT * FROM estaciones NATURAL JOIN lineas WHERE estacion = "estacion";
*/

/*
Dado el indice de una estaciones encuera las estacioneses anterior y siguiente
Ej 55 = balderas
linea_id se usa por si el antrior y siguiente estan en otra linea
*/
SELECT * FROM estaciones
WHERE (    id = 8 - 1 
        OR id = 8 + 1 
) AND linea_id = 1;

--- Todas las estaciones mas los atributos de la linea
SELECT * FROM estaciones JOIN lineas ON linea_id = lineas.id;



--- Si la estacion tiene transbordes, regresa con que lineas inersecta la estacion
SELECT * FROM estaciones JOIN lineas ON linea_id = lineas.id WHERE estacion = "Balderas";

SELECT id, linea_id FROM 
(SELECT * FROM estaciones WHERE estacion = "Balderas") t
NATURAL JOIN lineas;

--- Todas las estacioneses sin repetir

SELECT  DISTINCT estacion FROM estaciones;

SELECT estacion, COUNT(*) FROM estaciones GROUP BY estacion;

--- Query Mau
SELECT estacioneses.estacion, lineas.linea FROM 
estacioneses LEFT JOIN intersecciones 
ON estacioneses.id = intersecciones.estaciones_id 
LEFT JOIN lineas ON intersecciones.linea_id = lineas.id;

--- estacioneses con transbordes
SELECT estacion, COUNT(*) FROM estaciones 
GROUP BY estacion HAVING COUNT(*) > 1;

--- estacioneses con transbordes
SELECT * FROM estaciones a
JOIN
(SELECT estacion, COUNT(*) 
FROM estaciones 
GROUP BY estacion HAVING COUNT(*) > 1) b
ON a.estacion = b.estacion;

--- Numero de estacioneses en cada linea
SELECT linea_id, COUNT(*) 
FROM estaciones 
GROUP BY linea_id HAVING COUNT(*) > 1;

--- Numero de estacioneses en cada linea
SELECT * FROM linea a
NATURAL JOIN(
SELECT linea_id, COUNT(*) 
FROM estaciones 
GROUP BY linea_id HAVING COUNT(*) > 1);



