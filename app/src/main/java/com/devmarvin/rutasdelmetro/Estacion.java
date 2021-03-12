package com.devmarvin.rutasdelmetro;

import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class Estacion {
    private String nombre;
    private int id_linea;
    private boolean visitado;
    //private UUID id;
    private int anterior;
    private int index;
    private Linea mLinea;
    public Estacion(int index){ setIndex(index); }

    public void setLinea(Linea linea) {
        mLinea = linea;
    }
    public Linea getLinea() {
        return mLinea;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getId_linea() {
        return id_linea;
    }

    public void setId_linea(int id_linea) {
        this.id_linea = id_linea;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public int getAnterior() {
        return anterior;
    }

    public void setAnterior(int anterior) {
        this.anterior = anterior;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
