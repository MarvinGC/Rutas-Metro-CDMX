package com.devmarvin.rutasdelmetro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentComoLlegar extends Fragment {
    private LinearLayout estacionInicial;
    private LinearLayout estacionFinal;
    private TextView textEsInicial;
    private TextView textEsDestino;
    private Button botonBusqueda;
    private Estacion partida;
    private Estacion destino;
    CargandoDialog cargar;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_como_llegar,container,false);
        setEstaciones(view);
        cargar = new CargandoDialog(this.getActivity());
        estacionInicial = view.findViewById(R.id.estacion_inicial);
        estacionInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ActivityBuscar.newIntent(getActivity(), 0);
                startActivity(intent);
            }
        });
        estacionFinal = view.findViewById(R.id.estacion_final);
        estacionFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ActivityBuscar.newIntent(getActivity(), 1);
                startActivity(intent);
            }
        });
        botonBusqueda = view.findViewById(R.id.busqueda);
        botonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (partida != null && destino != null){
                    /*
                    cargar.iniciarCargandoDialog(partida.getIndex(),destino.getIndex());
                    */
                    Intent intent = ActivityRutaFinal.newIntent(
                            getActivity(),
                            new int[] {partida.getIndex(),destino.getIndex()}
                            );
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(),
                            "Campos incompletos",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void setEstaciones(View view) {
        partida = EstacionLab.get(getActivity()).getPrimera();
        destino = EstacionLab.get(getActivity()).getSegunda();
        if(partida != null) {
            textEsInicial = view.findViewById(R.id.text_es_partida);
            textEsInicial.setText(partida.getNombre());
        }
        if(destino != null) {
            textEsDestino = view.findViewById(R.id.text_es_destino);
            textEsDestino.setText(destino.getNombre());
        }
    }
}
