package com.acevedo.educonnect.estudiante.ui.cursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.acevedo.educonnect.commonresources.Adapters.TareaAdapter;
import com.acevedo.educonnect.commonresources.Adapters.TareaEstudianteAdapter;
import com.acevedo.educonnect.commonresources.Clases.Tarea;
import com.acevedo.educonnect.commonresources.Util.Util;
import com.acevedo.educonnect.estudiante.R;
import com.acevedo.educonnect.estudiante.ui.cursos.trabajo.TrabajoRevisadoActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListTareasActivity extends AppCompatActivity {

    SwipeRefreshLayout srlActualizarTareas;
    LinearLayout llRegresar;
    FloatingActionButton fabAgregarTarea;
    RecyclerView rvTareas;
    RequestQueue requestQueue;
    List<Tarea> tareaList;

    int id_curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tareas);

        srlActualizarTareas = findViewById(R.id.srlActualizarTareas);
        llRegresar = findViewById(R.id.llRegresar);
        rvTareas = findViewById(R.id.rvTareas);
        rvTareas.setHasFixedSize(true);
        rvTareas.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        tareaList = new ArrayList<>();
        cargarTareas();

        srlActualizarTareas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tareaList.clear();
                cargarTareas();
                srlActualizarTareas.setRefreshing(false);
            }
        });

        llRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        
    }

    private void cargarTareas() {
        SharedPreferences preferences = getSharedPreferences("usuarioLoginEstudiante", Context.MODE_PRIVATE);
        int id_usuario = preferences.getInt("id",0);
        id_curso = getIntent().getIntExtra("id_curso",0);
        String url = Util.RUTA_TAREAS+"?id_curso="+id_curso + "&id_usuario="+id_usuario;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tareas");
                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id =jsonObject.getInt("id");
                        String titulo = jsonObject.getString("titulo");
                        String descripcion =jsonObject.getString("descripcion");
                        String fecha_limite =jsonObject.getString("fecha_limite");
                        String retroalimentacion =jsonObject.getString("retroalimentacion");
                        String url_trabajo =jsonObject.getString("url_trabajo");
                        int nota = jsonObject.getInt("nota");
                        Tarea tarea = new Tarea(id,titulo,descripcion,fecha_limite,retroalimentacion,url_trabajo,nota);
                        tareaList.add(tarea);
                    }
                    TareaEstudianteAdapter adapter = new TareaEstudianteAdapter(ListTareasActivity.this,tareaList);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            selectTarea(view);
                        }
                    });

                    rvTareas.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListTareasActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void selectTarea(View view) {
        //datos del item seleccionado
        int id = tareaList.get(rvTareas.getChildAdapterPosition(view)).getId();
        String titulo = tareaList.get(rvTareas.getChildAdapterPosition(view)).getTitulo();
        String descripcion = tareaList.get(rvTareas.getChildAdapterPosition(view)).getDescripcion();
        String fecha_limite = tareaList.get(rvTareas.getChildAdapterPosition(view)).getFecha_limite();
        String url_trabajo = tareaList.get(rvTareas.getChildAdapterPosition(view)).getUrl_trabajo();
        String retroalimentacion = tareaList.get(rvTareas.getChildAdapterPosition(view)).getRetroalimentacion();
        int nota = tareaList.get(rvTareas.getChildAdapterPosition(view)).getNota();

        if(url_trabajo.length() > 10 && nota < 1 && retroalimentacion.length() < 5){
            Toast.makeText(this, " 1. Trabajo enviado, esperando revision", Toast.LENGTH_SHORT).show();
        }
        if(url_trabajo.length() < 5){
            Toast.makeText(this, "2. No enviaste tarea", Toast.LENGTH_SHORT).show();
        }

        if(retroalimentacion.length() >= 5 && nota > 0){
            Intent intent = new Intent(this, TrabajoRevisadoActivity.class);
            startActivity(intent);
        }
    }
}