package database;

public class MetroDbEsquema {
    public class TablaEstacion {
        public static final String Nombre = "estacion";
        public class Cols {
            public static final String idEstacion = "id_estacion";
            public static final String nombreEstacion = "estacion";
            public static final String lineaId = "id_linea";
        }
    }
    public class TablaLinea{
        public static final String Nombre = "linea";
        public class Cols {
            public static final String lineaId = "id_linea";
            public static final String nombreLinea = "linea";
            public static final String color = "color";
        }
    }
}
