package com.acevedo.educonnect.ui.docente;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acevedo.educonnect.R;
import com.acevedo.educonnect.commonresources.Adapters.PreguntaAdapter;
import com.acevedo.educonnect.commonresources.Clases.Alternativas;
import com.acevedo.educonnect.commonresources.Clases.Preguntas;
import com.acevedo.educonnect.commonresources.Util.Util;
import com.acevedo.educonnect.ui.docente.pregunta.CrearPreguntaActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PreguntasFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    FloatingActionButton crearPregunta;

    RecyclerView rvPreguntas;
    ArrayList<Preguntas> listaPreguntas;
    ProgressDialog progreso;

    RequestQueue requestQueue;

    JsonObjectRequest jsonObjectRequest;
    JsonArrayRequest jsonArrayRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista =  inflater.inflate(R.layout.fragment_preguntas, container, false);
        crearPregunta = vista.findViewById(R.id.fabAgregarPregunta);


        crearPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CrearPreguntaActivity.class);
                startActivity(intent);
            }
        });
// Inicializar la lista de carreras y el RecyclerView
        listaPreguntas = new ArrayList<>();
        rvPreguntas = vista.findViewById(R.id.rvPreguntas);
        rvPreguntas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPreguntas.setHasFixedSize(true);

        requestQueue = Volley.newRequestQueue(getContext());
        cargarListaPreguntas();
        return vista;
    }
//    private void cargarListaPreguntas() {
//        progreso = new ProgressDialog(getContext());
//        progreso.setMessage("Buscando Preguntas");
//        progreso.show();
//        String url = Util.RUTA_LIST_PREGUNTAS;
//
//        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
//        requestQueue.add(jsonObjectRequest);
//
//    }


    @Override
    public void onResponse(JSONObject response) {
//        Preguntas preguntas = null;
//        progreso.hide();
//        JSONArray json = response.optJSONArray("pregunta");
//
//        try {
//            for(int i=0; i<json.length();i++){
//                preguntas = new Preguntas();
//
//                JSONObject jsonObject = null;
//                jsonObject = json.getJSONObject(i);
//
//                preguntas.setId_pregunta(jsonObject.getInt("id"));
//                preguntas.setNombre(jsonObject.getString("nombre"));
//                preguntas.setRetroalimentacion(jsonObject.getString("retroalimentacion"));
//
//
//
//                listaPreguntas.add(preguntas);
//            }
//            PreguntaAdapter adapter = new PreguntaAdapter(getContext(),listaPreguntas);
//            rvPreguntas.setAdapter(adapter);
//
//        }catch (Exception e){ e.printStackTrace();}
    }

    @Override
    public void onErrorResponse(VolleyError error) {
//        Log.i("error en Volley",error.toString());
    }
    private void cargarListaPreguntas() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Buscando Preguntas");
        progreso.show();
        String url = Util.RUTA + "/list_preguntas_alternativas.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Preguntas> listaPreguntas = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Preguntas preguntas = new Preguntas();

                                preguntas.setId_pregunta(jsonObject.getInt("id"));
                                preguntas.setNombre(jsonObject.getString("nombre"));
                                preguntas.setRetroalimentacion(jsonObject.getString("retroalimentacion"));

                                // Obtener las alternativas de la pregunta
                                JSONArray jsonAlternativas = jsonObject.optJSONArray("alternativas");
                                if (jsonAlternativas != null) {
                                    List<Alternativas> alternativas = new ArrayList<>();
                                    for (int j = 0; j < jsonAlternativas.length(); j++) {
                                        JSONObject jsonAlternativa = jsonAlternativas.getJSONObject(j);
                                        Alternativas alternativa = new Alternativas();
                                        alternativa.setIdAlternativa(jsonAlternativa.getInt("id"));
                                        alternativa.setNombreAlternativa(jsonAlternativa.getString("nombre"));
                                        alternativas.add(alternativa);
                                    }
                                    preguntas.setAlternativas(alternativas);
                                }

                                listaPreguntas.add(preguntas);
                            }

                            PreguntaAdapter adapter = new PreguntaAdapter(getContext(), listaPreguntas);
                            rvPreguntas.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progreso.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error en Volley", error.toString());
                        progreso.hide();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}