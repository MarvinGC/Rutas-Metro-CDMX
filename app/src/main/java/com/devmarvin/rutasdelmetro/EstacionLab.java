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

public class EstacionLab {

    private static  Estacion primera;
    private static  Estacion segunda;
    private static EstacionLab sEstacionLab;
    private static List<Estacion> metro_list;
    private static List<Estacion> ruta_final;
    private SQLiteDatabase metro;


    public static EstacionLab get(Context context){
            if(sEstacionLab == null){
                Log.d("EstacionLab","sEstacionLab es null");
                return new EstacionLab(context);
            }
            else {
                return sEstacionLab;
            }
    }

    public static Estacion getPrimera() {
        return primera;
    }

    public static void setPrimera(Estacion primera) {
        EstacionLab.primera = primera;
    }

    public static Estacion getSegunda() {
        return segunda;
    }

    public static void setSegunda(Estacion segunda) {
        EstacionLab.segunda = segunda;
    }

    public static List<Estacion> getMetro_list() {
        return metro_list;
    }

    public static void setMetro_list(List<Estacion> metro_list) {
        EstacionLab.metro_list = metro_list;
    }

    public static List<Estacion> getRuta_final() {
        return ruta_final;
    }

    public static void setRuta_final(List<Estacion> ruta_final) {
        EstacionLab.ruta_final = ruta_final;
    }

    public EstacionLab(Context context){
        EstacionHelper e = new EstacionHelper(context);
        try {
            e.createDatabase();
            metro = e.getReadableDatabase();
            metro_list = getEstaciones();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private List<Estacion> getTransbordes(Estacion estacion) {
         /*
        //Transbordes
        SELECT * FROM estacion NATURAL JOIN linea WHERE estacion = "estacion;
         */
        String consulta = "SELECT * FROM estacion NATURAL JOIN linea WHERE estacion =\""+estacion.getNombre()+"\";";
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
        /*Anterior y Siguiente */
        /*
        SELECT * FROM estacion
        WHERE ( id_estacion = index - 1 OR id_estacion = index + 1 ) AND id_linea = linea;
        * */
        //Ajustar index a la base
        index++;
        String consulta = "SELECT * FROM estacion" +
                " WHERE ( id_estacion = " + index + " - 1 " +
                " OR id_estacion = " + index + " + 1 )" +
                " AND id_linea = "+ linea +";";
        List<Integer> estaciones = new ArrayList<>();
        Cursor cursor = metro.rawQuery(
                consulta,null
        );
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                estaciones.add(cursor.getInt(cursor.getColumnIndex(
                        MetroDbEsquema.TablaEstacion.Cols.id_estacion
                ))/* index - 1 para ajustar con el arreglo */ -1);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return estaciones;
    }
    public List<Integer> getAristas(Estacion estacion){
        List<Integer> aristas = new LinkedList<>();
        for(Estacion e : getTransbordes(estacion)){
            aristas.addAll(
            getAnteriorSiguiente(e.getIndex(),e.getId_linea())
            );
        }
        /*
        for(int x : aristas){
            Log.d("BFS",metro_list.get(x).getNombre());
        }
        */
        return aristas;
    }
    private List<Estacion> getEstaciones() {

        // SELECT * FROM estacion NATURAL JOIN linea;

        String consulta = "SELECT * FROM estacion NATURAL JOIN linea;";
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

    private Estacion getEstacion(Cursor cursor){
        Estacion estacion = new Estacion(cursor.getInt(cursor.getColumnIndex(
                MetroDbEsquema.TablaEstacion.Cols.id_estacion
        ))/* index - 1 para ajustar con el arreglo */  -1 );
        estacion.setNombre(
                cursor.getString(cursor.getColumnIndex(
                 MetroDbEsquema.TablaEstacion.Cols.estacion))
                );
        estacion.setId_linea(
                cursor.getInt(cursor.getColumnIndex(
                        MetroDbEsquema.TablaEstacion.Cols.id_linea))
        );
        estacion.setLinea(
                new Linea(
                    cursor.getInt(cursor.getColumnIndex(
                            MetroDbEsquema.TablaEstacion.Cols.id_linea)),
                    cursor.getString(cursor.getColumnIndex(
                            MetroDbEsquema.TablaLinea.Cols.linea)),
                    cursor.getString(cursor.getColumnIndex(
                            MetroDbEsquema.TablaLinea.Cols.color))
                )
        );
        return estacion;
    }
    public Estacion getEstacion(int index){
        if(index != -1)
            return metro_list.get(index);
        else
            return null;
    }
}
