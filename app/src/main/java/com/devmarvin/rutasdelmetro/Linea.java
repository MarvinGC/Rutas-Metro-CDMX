package com.devmarvin.rutasdelmetro;

public class Linea {
    private String nombre;
    private String color;
    private int index;
    public Linea(int index, String nombre, String color){
        setIndex(index);
        setNombre(nombre);
        setColor(color);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
