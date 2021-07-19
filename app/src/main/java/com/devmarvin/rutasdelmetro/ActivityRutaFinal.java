package com.devmarvin.rutasdelmetro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public static Intent newIntent(Context context, int[] inicialFinal) {
        /*
            inicialFinal -> Contiene el indice de inicio y final de la busqueda
            Donde:
            0 es la estacion inicial
            1 es la estacion final
       */

        Intent intent = new Intent(context, ActivityRutaFinal.class);
        intent.putExtra(EXTRA_INI_FIN,inicialFinal);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_final);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        int[] incialFinal = getIntent().getIntArrayExtra(EXTRA_INI_FIN);
        Log.d("RutaFinal",
                   "Estacion inicial "+ incialFinal[0]+
                        " estacion final "  + incialFinal[1]);
        // Se ejecuta el Algoritmo Breadth First Search
        mEstaciones = new Busqueda(incialFinal[0], incialFinal[1], getApplicationContext()).BFS();
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
        private ImageView iconoEstacion;
        private TextView nombreEstacion;
        private TextView nombreLinea;
        private TextView colorLinea;

        public EstacionHolder(View itemView) {
            super(itemView);
            nombreEstacion = itemView.findViewById(R.id.nombre_estacion);
            iconoEstacion = itemView.findViewById(R.id.icono_estacion);
            nombreLinea = itemView.findViewById(R.id.nombre_linea);
            colorLinea = itemView.findViewById(R.id.color_linea);
        }

        public void bind(Estacion e) {
            mEstacion = e;
            iconoEstacion.setImageResource(
                    getImageMipmap(mEstacion.getRutaLogo(),getApplicationContext()));
            nombreEstacion.setText(mEstacion.getNombre());
            nombreLinea.setText(mEstacion.getLinea().getNombre());
            colorLinea.setText(mEstacion.getLinea().getColor());
        }
        public int getImageMipmap(String imageName, Context context) {
            int drawableResourceId = context.getResources().
                    getIdentifier(imageName, "mipmap", context.getPackageName());
            Log.d("est",drawableResourceId+" "+imageName);
            return (drawableResourceId == 0) ? R.mipmap.ic_launcher : drawableResourceId;
        }
    }
}