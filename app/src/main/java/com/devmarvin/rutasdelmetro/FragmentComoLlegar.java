package com.devmarvin.rutasdelmetro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentComoLlegar extends Fragment {
    private LinearLayout estacionPartida;
    private LinearLayout estacionFinal;
    private ImageView iconoEstacionPartida;
    private ImageView iconoEstacionDestino;
    private TextView textEstacionPartida;
    private TextView textEstacionDestino;
    private TextView textLineaPartida;
    private TextView textLineaDestino;
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
        estacionPartida = view.findViewById(R.id.estacion_inicial);
        estacionPartida.setOnClickListener(new View.OnClickListener() {
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
            textEstacionPartida = view.findViewById(R.id.text_estacion_partida);
            textLineaPartida = view.findViewById(R.id.text_linea_partida);
            iconoEstacionPartida = view.findViewById(R.id.icono_estacion_partida);
            iconoEstacionPartida.setImageResource(
                    getImageMipmap(partida.getRutaLogo(), getContext()));
            textEstacionPartida.setText(partida.getNombre());
            textLineaPartida.setText(partida.getLinea().getNombre());
        }
        if(destino != null) {
            textEstacionDestino = view.findViewById(R.id.text_estacion_destino);
            textLineaDestino = view.findViewById(R.id.text_linea_destino);
            iconoEstacionDestino = view.findViewById(R.id.icono_estacion_destino);
            iconoEstacionDestino.setImageResource(
                    getImageMipmap(destino.getRutaLogo(),getContext()));
            textEstacionDestino.setText(destino.getNombre());
            textLineaDestino.setText(destino.getLinea().getNombre());
        }
    }
    public int getImageMipmap(String imageName, Context context) {
        int drawableResourceId = context.getResources().
                getIdentifier(imageName, "mipmap", context.getPackageName());
        Log.d("est",drawableResourceId+" "+imageName);
        return (drawableResourceId == 0) ? R.mipmap.ic_launcher : drawableResourceId;
    }
}
