package database;

public class MetroDbEsquema {
    public class TablaEstacion {
        public static final String Nombre = "estaciones";
        public class Cols {
            public static final String idEstacion = "id_estacion";
            public static final String nombreEstacion = "estacion";
            public static final String lineaId = "linea_id";
            public static final String rutaLogo = "ruta_logo";
            public static final String tiempoSiguienteEstacion = "tiempo_siguiente_estacion";
        }
    }
    public class TablaLinea{
        public static final String Nombre = "lineas";
        public class Cols {
            public static final String idLinea = "id_linea";
            public static final String nombreLinea = "linea";
            public static final String color = "color";
        }
    }
}
