package com.acevedo.educonnect.ui.docente.curso.asistencia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.acevedo.educonnect.Adapters.EstudianteAdapter;
import com.acevedo.educonnect.Clases.Estudiante;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.commonresources.Util.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegistrarAsistenciaActivity extends AppCompatActivity {

    RecyclerView rvEstudiantes;
    RequestQueue requestQueue;
    List<Estudiante> estudianteList;

    JsonObjectRequest jsonObjectRequest;

    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String fechaActual = dateFormat.format(date);

    int curso_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_asistencia);
        rvEstudiantes = findViewById(R.id.rvEstudiantes);
        rvEstudiantes.setHasFixedSize(true);
        rvEstudiantes.setLayoutManager(new LinearLayoutManager(this));
        estudianteList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        cargarEstudiantes();
    }

    private void cargarEstudiantes() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando estudiantes...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        curso_id = getIntent().getIntExtra("id_curso",0);

        String url = Util.RUTA_ESTUDIANTES_CURSO+"?curso_id="+curso_id+"&fecha_actual="+fechaActual;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("estudiantes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String dni = jsonObject.getString("dni");
                        String nombres = jsonObject.getString("nombres");
                        String apellidos = jsonObject.getString("apellidos");
                        String estado = jsonObject.getString("estado");
                        Estudiante estudiante = new Estudiante(id, dni, nombres,apellidos,estado);
                        estudianteList.add(estudiante);
                    }
                    EstudianteAdapter adapter = new EstudianteAdapter(RegistrarAsistenciaActivity.this, estudianteList);


                    rvEstudiantes.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarAsistenciaActivity.this, "No se encontro estudiantes", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void registrarAsistencia(int usuario_id, String estado ){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando Cambios ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = Util.RUTA_REGISTRAR_ASISTENCIA;

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Mostrar mensaje de api
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud (si es necesario)
                        Toast.makeText(getApplicationContext(), "Error al enviar los datos", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario_id", usuario_id+"");
                params.put("curso_id", curso_id+"");
                params.put("fecha", fechaActual);
                params.put("estado", estado);
                return params;
            }
        };

        // Agregar la solicitud a la cola de Volley
        requestQueue.add(request);
    }
}