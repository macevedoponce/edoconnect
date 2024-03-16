package com.acevedo.educonnect.ui.docente.curso.examen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.acevedo.educonnect.R;
import com.acevedo.educonnect.commonresources.Adapters.EntregaTareaAdapter;
import com.acevedo.educonnect.commonresources.Clases.EntregaTareas;
import com.acevedo.educonnect.commonresources.Util.Util;
import com.acevedo.educonnect.ui.docente.curso.entregaTarea.DetalleEntregaTareaActivity;
import com.acevedo.educonnect.ui.docente.curso.entregaTarea.ListEntregaTareasActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BancoPreguntasActivity extends AppCompatActivity {

    RecyclerView rvBancoPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_preguntas);
        rvBancoPreguntas = findViewById(R.id.rvBancoPreguntas);
        rvBancoPreguntas.setHasFixedSize(true);
        rvBancoPreguntas.setLayoutManager(new LinearLayoutManager(this));

        //cargarPreguntas();
    }

//    private void cargarPreguntas() {
//        //int id_curso = getIntent().getIntExtra("id_curso",0);
//        String url = "http://192.168.18.53/eduConnectAPI/list_preguntas_alternativas.php";
//                //Util.RUTA_ENTREGA_TAREAS+"?id_curso="+id_curso;
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray jsonArray = response.getJSONArray("tareas_entregadas");
//                    for(int i = 0; i<jsonArray.length();i++){
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        int id =jsonObject.getInt("id");
//                        String retroalimentacion = jsonObject.getString("retroalimentacion");
//                        String url_trabajo =jsonObject.getString("url_trabajo");
//                        String nombres =jsonObject.getString("nombres");
//                        String apellidos =jsonObject.getString("apellidos");
//                        int nota =jsonObject.getInt("nota");
//                        EntregaTareas entregaTarea = new EntregaTareas(id,retroalimentacion,url_trabajo,nota,nombres,apellidos);
//                        entregaTareasList.add(entregaTarea);
//                    }
//                    EntregaTareaAdapter adapter = new EntregaTareaAdapter(ListEntregaTareasActivity.this,entregaTareasList);
//                    adapter.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            int id_entrega = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getId();
//                            int nota = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getNota();
//                            String nombres = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getUs_nombres();
//                            String apellidos = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getUs_apellidos();
//                            String retroalimentaicon = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getRetroalimentacion();
//                            String url_trabajo = entregaTareasList.get(rvEntregaTareas.getChildAdapterPosition(view)).getUrl_trabajo();
//
//                            Intent i = new Intent(ListEntregaTareasActivity.this, DetalleEntregaTareaActivity.class);
//                            i.putExtra("id_entrega", id_entrega);
//                            i.putExtra("nota", nota);
//                            i.putExtra("nombres", nombres);
//                            i.putExtra("apellidos", apellidos);
//                            i.putExtra("retroalimentaicon", retroalimentaicon);
//                            i.putExtra("url_trabajo", url_trabajo);
//                            startActivity(i);
//                        }
//                    });
//
//                    rvEntregaTareas.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                rvEntregaTareas.setVisibility(View.GONE);
//                tvContenidoVacio.setVisibility(View.VISIBLE);
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//    }
}