package database;

public class MetroDbEsquema {
    public class TablaEstacion {
        public static final String Nombre = "estacion";
        public class Cols {
            public static final String id_estacion = "id_estacion";
            public static final String estacion = "estacion";
            public static final String id_linea = "id_linea";
        }
    }
    public class TablaLinea{
        public static final String Nombre = "linea";
        public class Cols {
            public static final String id_linea = "id_linea";
            public static final String linea = "linea";
            public static final String color = "color";
        }
    }
}
