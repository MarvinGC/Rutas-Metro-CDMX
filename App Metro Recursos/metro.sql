-- Sqlite schema metro
-- Thu 28 Jan 2021 03:49:40 PM EST

--- execute .read metro.sql


--- show tables; == .tables
--- describe table; == .schema table
--- clear == .shell clear


-- -----------------------------------------------------
-- Table `metro`.`lineas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS 'lineas' (
  `id` INTEGER PRIMARY KEY, --- Esta columna es rowId actua como un primary key
  `linea` TEXT,
  `color` TEXT
);


-- -----------------------------------------------------
-- Table `metro`.`estaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS 'estaciones' (
  `id` INTEGER PRIMARY KEY, --- Esta columna es rowId actua como un primary key
  `linea_id` INTEGER,
  `estacion` TEXT,
  'ruta_logo' TEXT,
  'tiempo_siguiente_estacion' INT,
  FOREIGN KEY('linea_id') REFERENCES lineas('id')
);

