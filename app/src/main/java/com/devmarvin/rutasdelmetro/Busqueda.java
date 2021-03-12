package com.devmarvin.rutasdelmetro;

import android.content.Context;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Busqueda {
    /*
    Algoritmo Breadth First Search (BFS) (Primero en amplitud)
    */
    private int ini;
    private int fin;
    private List<Estacion> metro;
    private Context context;
    public Busqueda(int ini, int fin, Context context) {
        this.ini = ini;
        this.fin = fin;
        this.context = context;
        metro = EstacionLab.get(context).getMetro_list();
    }
    public List<Estacion> BFS(){
        setNoVistos();
        Queue<Integer> cola = new LinkedList<>();
        cola.add(ini);
        metro.get(ini).setVisitado(true);
        while(!cola.isEmpty()){
            int actual = cola.peek();
            cola.poll();
            List<Integer> aristas = EstacionLab.
                                    get(context).
                                    getAristas(metro.get(actual));
            for(int x : aristas){
                if(!metro.get(x).isVisitado()){
                    metro.get(x).setVisitado(true);
                    metro.get(x).setAnterior(actual);
                    cola.add(x);
                    if(x == fin){
                        cola.clear();
                        break;
                    }
                }
            }
        }
        return calcularRuta(fin);
    }

    private List<Estacion> calcularRuta(int fin) {
        Stack<Integer> pila = new Stack<>();
        while(fin != -1){
            pila.add(metro.get(fin).getIndex());
            fin = metro.get(fin).getAnterior();
        }
        List<Estacion> ruta = new LinkedList<>();
        while(!pila.isEmpty()){
            //Log.d("BFS",metro.get(pila.peek()).getNombre());
            ruta.add(metro.get(pila.peek()));
            pila.pop();
        }
        return ruta;
    }

    private void setNoVistos() {
        for(Estacion x: metro){
            x.setVisitado(false);
            x.setAnterior(-1);
        }
    }
}
