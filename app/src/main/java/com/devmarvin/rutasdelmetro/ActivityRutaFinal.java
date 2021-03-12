package com.devmarvin.rutasdelmetro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityRutaFinal extends AppCompatActivity {
    private static final String EXTRA_INI_FIN = "android.neuromante.rutasmetrosqlite.ini_fin";
    private List<Estacion> mEstaciones = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private EstacionAdapter mEstacionAdapter;

    public static Intent newIntent(Context context, int[] selector) {
        Intent intent = new Intent(context, ActivityRutaFinal.class);
        intent.putExtra(EXTRA_INI_FIN,selector);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_final);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        int[] ini_fin = getIntent().getIntArrayExtra(EXTRA_INI_FIN);
        Log.d("ARF","Estaciones ->"+ini_fin[0]+" "+ini_fin[1]);
        mEstaciones = new Busqueda(ini_fin[0],ini_fin[1], getApplicationContext()).BFS();
        UpdateUI();
    }
    private void UpdateUI() {
        mEstacionAdapter = new EstacionAdapter(mEstaciones);
        mRecyclerView.setAdapter(mEstacionAdapter);
    }

    public class EstacionAdapter extends RecyclerView.Adapter<EstacionHolder>{
        List<Estacion> mEstacionList;
        public EstacionAdapter(List<Estacion> lista){
            mEstacionList = lista;
        }
        @Override
        public EstacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.fila_estacion_layout, parent, false);
            EstacionHolder es = new EstacionHolder(v);
            return es;
        }

        @Override
        public void onBindViewHolder(EstacionHolder holder, int position) {
            Estacion estacion = mEstacionList.get(position);
            holder.bind(estacion);
        }

        @Override
        public int getItemCount() {
            return mEstacionList.size();
        }
    }
    public class EstacionHolder extends RecyclerView.ViewHolder {
        private Estacion mEstacion;
        private TextView nombreEstacion;
        private TextView nombreLinea;
        private TextView colorLinea;

        public EstacionHolder(View itemView) {
            super(itemView);
            nombreEstacion = itemView.findViewById(R.id.nombre_estacion);
            nombreLinea = itemView.findViewById(R.id.nombre_linea);
            colorLinea = itemView.findViewById(R.id.color_linea);
        }

        public void bind(Estacion e) {
            mEstacion = e;
            nombreEstacion.setText(mEstacion.getNombre());
            //nombreLinea.setText(mEstacion.getLinea().getNombre());
            //colorLinea.setText(mEstacion.getLinea().getColor());
        }
    }
}