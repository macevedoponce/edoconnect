package com.acevedo.educonnect.ui.docente.curso.entregaTarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acevedo.educonnect.Adapters.EntregaTareaAdapter;
import com.acevedo.educonnect.Clases.EntregaTareas;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.Util.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListEntregaTareasActivity extends AppCompatActivity {

    LinearLayout llRegresar;
    TextView tvTituloTarea;
    SwipeRefreshLayout srlActualizarEntregaTareas;
    RecyclerView rvEntregaTareas;
    RequestQueue requestQueue;
    List<EntregaTareas> entregaTareasList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_tareas);
        tvTituloTarea = findViewById(R.id.tvTituloTarea);
        llRegresar = findViewById(R.id.llRegresar);
        srlActualizarEntregaTareas = findViewById(R.id.srlActualizarEntregaTareas);
        rvEntregaTareas = findViewById(R.id.rvEntregaTareas);
        rvEntregaTareas.setHasFixedSize(true);
        rvEntregaTareas.setLayoutManager(new GridLayoutManager(ListEntregaTareasActivity.this,2));
        requestQueue = Volley.newRequestQueue(this);
        entregaTareasList = new ArrayList<>();
        cargarEntregaTareas();


        srlActualizarEntregaTareas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ListEntregaTareasActivity.this, "actualizando rvEntregas", Toast.LENGTH_SHORT).show();
//                tareaList.clear();
//                cargarTareas();
//                srlActualizarTareas.setRefreshing(false);
            }
        });

        String titulo = getIntent().getStringExtra("titulo");
        tvTituloTarea.setText(titulo);

        llRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void cargarEntregaTareas() {
        int id_tarea = getIntent().getIntExtra("id_tarea",0);
        String url = Util.RUTA_ENTREGA_TAREAS+"?id_tarea="+id_tarea;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tareas_entregadas");
                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id =jsonObject.getInt("id");
                        String retroalimentacion = jsonObject.getString("retroalimentacion");
                        String url_trabajo =jsonObject.getString("url_trabajo");
                        String nombres =jsonObject.getString("nombres");
                        String apellidos =jsonObject.getString("apellidos");
                        int nota =jsonObject.getInt("nota");
                        EntregaTareas entregaTarea = new EntregaTareas(id,retroalimentacion,url_trabajo,nota,nombres,apellidos);
                        entregaTareasList.add(entregaTarea);
                    }
                    EntregaTareaAdapter adapter = new EntregaTareaAdapter(ListEntregaTareasActivity.this,entregaTareasList);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //dialogMenu(view);
                        }
                    });

                    rvEntregaTareas.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListEntregaTareasActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}