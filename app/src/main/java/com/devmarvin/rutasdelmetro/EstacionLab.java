package com.devmarvin.rutasdelmetro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import database.EstacionHelper;
import database.MetroDbEsquema;


/*
    Consideraciónes:
    Ver como quitar el asterisco en las consultas
    Crear Mas funciones para que se más entendible
 */
public class EstacionLab {

    private static Estacion primera;
    private static Estacion segunda;
    private static EstacionLab estacionLabEstatica;
    private static List<Estacion> estacionesMetro;
    private static List<Estacion> rutaMasCorta;
    private SQLiteDatabase metro;

    public EstacionLab(Context context){
        EstacionHelper e = new EstacionHelper(context);
        try {
            e.createDatabase();
            metro = e.getReadableDatabase();
            estacionesMetro = getEstaciones();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static EstacionLab get(Context context){
            if(estacionLabEstatica == null){
                Log.d("EstLab","estacionLab es null");
                return new EstacionLab(context);
            }
            else {
                Log.d("EstLab","estacionLab existe");
                return estacionLabEstatica;
            }
    }

    public static Estacion getPrimera() {
        return primera;
    }

    public static Estacion getSegunda() {
        return segunda;
    }

    public static void setPrimera(int index) {
        EstacionLab.primera = getEstacion(index);
    }

    public static void setSegunda(int index) {
        EstacionLab.segunda = getEstacion(index);
    }

    public static List<Estacion> getEstacionesMetro() {
        return estacionesMetro;
    }

    public static void setEstacionesMetro(List<Estacion> estacionesMetro) {
        EstacionLab.estacionesMetro = estacionesMetro;
    }
    public static List<Estacion> getRutaMasCorta() {
        return rutaMasCorta;
    }

    public static void setRutaMasCorta(List<Estacion> rutaMasCorta) {
        EstacionLab.rutaMasCorta = rutaMasCorta;
    }

    public static Estacion getEstacion(int index){
        return (index != -1) ? estacionesMetro.get(index) : null;
    }

    public List<Integer> getAristas(Estacion estacion){
        List<Integer> aristas = new LinkedList<>();
        for(Estacion e : getTransbordes(estacion)){
            aristas.addAll(
                    getAnteriorSiguiente(e.getIndex(),e.getLineaId())
            );
        }
        /*// Debug
        for(int x : aristas){
            Log.d("BFS",estacionesMetro.get(x).getNombre());
        }
         */
        return aristas;
    }
    public List<Integer> getTransbordesInt(Estacion estacion) {
        /*
        Si la estacion tiene transbordes, regresa con que lineas inersecta la estacion
        SELECT * FROM estaciones JOIN lineas ON linea_id = id_linea WHERE estacion = "estacion";
         */
        String consulta =  " SELECT * FROM "+
                MetroDbEsquema.TablaEstacion.Nombre +
                " JOIN "+ MetroDbEsquema.TablaLinea.Nombre + " ON "+
                MetroDbEsquema.TablaEstacion.Cols.lineaId +" = " +
                MetroDbEsquema.TablaLinea.Cols.idLinea +
                " WHERE "+ MetroDbEsquema.TablaEstacion.Cols.nombreEstacion +
                " =\"" + estacion.getNombre() + "\";";
        List<Integer> transbordes = new ArrayList<>();
        Cursor cursor = metro.rawQuery(
                consulta,null
        );
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                transbordes.add(cursor.getInt(cursor.getColumnIndex(
                        MetroDbEsquema.TablaEstacion.Cols.idEstacion
                ))/* index - 1 para ajustar con el arreglo */ - 1);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return transbordes;
    }
    private List<Estacion> getTransbordes(Estacion estacion) {
         /*
        Si la estacion tiene transbordes, regresa con que lineas inersecta la estacion
        SELECT * FROM estaciones JOIN lineas ON linea_id = id_linea WHERE estacion = "estacion";
         */
        String consulta =  " SELECT * FROM "+
                             MetroDbEsquema.TablaEstacion.Nombre +
                           " JOIN "+ MetroDbEsquema.TablaLinea.Nombre + " ON "+
                            MetroDbEsquema.TablaEstacion.Cols.lineaId +" = " +
                            MetroDbEsquema.TablaLinea.Cols.idLinea +
                           " WHERE "+ MetroDbEsquema.TablaEstacion.Cols.nombreEstacion +
                           " =\"" + estacion.getNombre() + "\";";
        List<Estacion> transbordes = new ArrayList<>();
        Cursor cursor = metro.rawQuery(
                consulta,null
        );
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                transbordes.add(getEstacion(cursor));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return transbordes;
    }
    private List<Integer> getAnteriorSiguiente(int index,int linea){
        // Ajustar index del arreglo al de la base
        index++;
        /*
            Dado el indice de una estaciones encuera las estacioneses anterior y siguiente
            linea_id se usa por si el antrior y siguiente estan en otra linea
            Ej
            index = 55    (balderas)
            linea_id = 3
            SELECT * FROM estaciones
            WHERE (    id_estacion = 55 - 1
                    OR id_estacion = 55 + 1
            ) AND linea_id = 3;
        */
        String consulta = "SELECT * FROM "+ MetroDbEsquema.TablaEstacion.Nombre +
                " WHERE ( "+ MetroDbEsquema.TablaEstacion.Cols.idEstacion + " = " + index + " - 1 " +
                " OR "+ MetroDbEsquema.TablaEstacion.Cols.idEstacion +" = " + index + " + 1 )" +
                " AND "+ MetroDbEsquema.TablaEstacion.Cols.lineaId +" = "+ linea +";";
        List<Integer> estaciones = new ArrayList<>();
        Cursor cursor = metro.rawQuery(
                consulta,null
        );
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                estaciones.add(cursor.getInt(cursor.getColumnIndex(
                        MetroDbEsquema.TablaEstacion.Cols.idEstacion
                ))/* index - 1 para ajustar con el arreglo */ -1);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return estaciones;
    }

    private List<Estacion> getEstaciones() {

        /*
        --- Todas las estaciones mas los atributos de la linea
          SELECT * FROM estaciones JOIN lineas ON linea_id = id_linea;
         */
        String consulta =  "SELECT * FROM " +
                            MetroDbEsquema.TablaEstacion.Nombre +
                            " JOIN " + MetroDbEsquema.TablaLinea.Nombre + " ON " +
                            MetroDbEsquema.TablaEstacion.Cols.lineaId + " = " +
                            MetroDbEsquema.TablaLinea.Cols.idLinea;
        List<Estacion> estaciones = new ArrayList<>();
        Cursor cursor = metro.rawQuery(
                consulta,null
        );
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                estaciones.add(getEstacion(cursor));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return estaciones;
    }

    private Estacion getEstacion(Cursor cursor) {
        Estacion estacion = new Estacion(cursor.getInt(cursor.getColumnIndex(
                MetroDbEsquema.TablaEstacion.Cols.idEstacion
        ))/* index - 1 para ajustar con el arreglo */ - 1);
        estacion.setNombre(
                cursor.getString(cursor.getColumnIndex(
                        MetroDbEsquema.TablaEstacion.Cols.nombreEstacion))
        );
        estacion.setLineaId(
                cursor.getInt(cursor.getColumnIndex(
                        MetroDbEsquema.TablaEstacion.Cols.lineaId))
        );
        estacion.setRutaLogo(
                cursor.getString(cursor.getColumnIndex(
                        MetroDbEsquema.TablaEstacion.Cols.rutaLogo
                ))
        );
        estacion.setLinea(
                new Linea(
                        cursor.getInt(cursor.getColumnIndex(
                                MetroDbEsquema.TablaEstacion.Cols.lineaId)),
                        cursor.getString(cursor.getColumnIndex(
                                MetroDbEsquema.TablaLinea.Cols.nombreLinea)),
                        cursor.getString(cursor.getColumnIndex(
                                MetroDbEsquema.TablaLinea.Cols.color))
                )
        );
        return estacion;
    }
}
