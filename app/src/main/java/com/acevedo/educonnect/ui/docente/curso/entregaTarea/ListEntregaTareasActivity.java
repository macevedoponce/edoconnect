package com.acevedo.educonnect.ui.docente.curso.entregaTarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acevedo.educonnect.Adapters.EntregaTareaAdapter;
import com.acevedo.educonnect.Clases.EntregaTareas;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.commonresources.Util.Util;
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
    TextView tvTituloTarea,tvContenidoVacio;
    SwipeRefreshLayout srlActualizarEntregaTareas;
    RecyclerView rvEntregaTareas;
    RequestQueue requestQueue;
    List<EntregaTareas> entregaTareasList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_tareas);
        tvContenidoVacio = findViewById(R.id.tvContenidoVacio);
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
                entregaTareasList.clear();
                cargarEntregaTareas();
                srlActualizarEntregaTareas.setRefreshing(false);
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
                tvContenidoVacio.setVisibility(View.GONE);
                rvEntregaTareas.setVisibility(View.VISIBLE);
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
                            int id_entrega = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getId();
                            int nota = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getNota();
                            String nombres = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getUs_nombres();
                            String apellidos = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getUs_apellidos();
                            String retroalimentaicon = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getRetroalimentacion();
                            String url_trabajo = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getUrl_trabajo();

                            Intent i = new Intent(ListEntregaTareasActivity.this, DetalleEntregaTareaActivity.class);
                            i.putExtra("id_entrega", id_entrega);
                            i.putExtra("nota", nota);
                            i.putExtra("nombres", nombres);
                            i.putExtra("apellidos", apellidos);
                            i.putExtra("retroalimentaicon", retroalimentaicon);
                            i.putExtra("url_trabajo", url_trabajo);
                            startActivity(i);
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
                rvEntregaTareas.setVisibility(View.GONE);
                tvContenidoVacio.setVisibility(View.VISIBLE);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}