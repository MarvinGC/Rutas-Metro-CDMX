package com.devmarvin.rutasdelmetro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityBuscar extends AppCompatActivity {
    private List<Estacion> mEstaciones = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private EstacionAdapter mEstacionAdapter;
    private static final String EXTRA_SELECTOR = "android.neuromante.rutasmetrosqlite.selector";
    private int estacion;

    public static Intent newIntent(Context context, int selector) {
        /*
        * selector = 0 -> Estacion Partida
        * selector = 1 -> Estacion Destino
        * */
        Intent intent = new Intent(context, ActivityBuscar.class);
        intent.putExtra(EXTRA_SELECTOR,selector);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_estaciones);
        mRecyclerView = findViewById(R.id.estacion_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mEstaciones = EstacionLab.get(getApplicationContext()).getMetro_list();

        estacion = getIntent().getIntExtra(EXTRA_SELECTOR,-1);

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
    public class EstacionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Estacion mEstacion;
        private TextView nombreEstacion;
        private TextView nombreLinea;
        private TextView colorLinea;

        public EstacionHolder(View itemView) {
            super(itemView);
            this.itemView.setOnClickListener(this);
            nombreEstacion = itemView.findViewById(R.id.nombre_estacion);
            nombreLinea = itemView.findViewById(R.id.nombre_linea);
            colorLinea = itemView.findViewById(R.id.color_linea);
        }

        @Override
        public void onClick(View view) {
            setCampo(estacion,mEstacion.getIndex());
            Intent intent = new Intent(getApplicationContext(),ActivityComoLlegar.class);
            startActivity(intent);
        }
        private void setCampo(int selector, int index) {
            if(selector == 0){
                EstacionLab tem = EstacionLab.get(getApplicationContext());
                tem.setPrimera(tem.getEstacion(index));
            }
            if(selector == 1){
                EstacionLab tem = EstacionLab.get(getApplicationContext());
                tem.setSegunda(tem.getEstacion(index));
            }
        }

        public void bind(Estacion e) {
            mEstacion = e;
            nombreEstacion.setText(mEstacion.getNombre());
            //nombreLinea.setText(mEstacion.getLinea().getNombre());
            //colorLinea.setText(mEstacion.getLinea().getColor());
        }
    }
}
