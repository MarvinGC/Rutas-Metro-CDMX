package com.devmarvin.rutasdelmetro;

public class Estacion {
    private String nombre;
    private String rutaLogo;
    private int lineaId;
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


    public int getLineaId() {
        return lineaId;
    }

    public void setLineaId(int lineaId) {
        this.lineaId = lineaId;
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

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }
}
