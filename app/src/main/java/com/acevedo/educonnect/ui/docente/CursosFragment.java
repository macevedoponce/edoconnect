package com.acevedo.educonnect.ui.docente;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.acevedo.educonnect.commonresources.Adapters.CursoAdapter;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.commonresources.Clases.Curso;

import com.acevedo.educonnect.commonresources.Util.Util;
import com.acevedo.educonnect.ui.docente.curso.asistencia.RegistrarAsistenciaActivity;
import com.acevedo.educonnect.ui.docente.curso.tarea.ListTareasActivity;
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


public class CursosFragment extends Fragment {

    RecyclerView rvCursos;
    RequestQueue requestQueue;
    List<Curso> cursosList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_cursos, container, false);
        rvCursos = vista.findViewById(R.id.rvCursos);
        rvCursos.setHasFixedSize(true);
        rvCursos.setLayoutManager(new LinearLayoutManager(getContext()));
        requestQueue = Volley.newRequestQueue(getContext());
        cursosList = new ArrayList<>();
        cargarCursos();
        return vista;
    }

    private void cargarCursos() {

        SharedPreferences preferences = getContext().getSharedPreferences("usuarioLoginDocente", Context.MODE_PRIVATE);
        int id = preferences.getInt("id",0);

        String url = Util.RUTA_CURSOS+"?id_usuario="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("cursos");
                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id =jsonObject.getInt("id");
                        String cursoCodigo = jsonObject.getString("cursoCodigo");
                        String cursoNombre =jsonObject.getString("cursoNombre");
                        String grado =jsonObject.getString("grado");
                        String seccion =jsonObject.getString("seccion");
                        String img_url = jsonObject.getString("img_url");
                        Curso curso = new Curso(id,cursoCodigo,cursoNombre,grado,seccion,img_url);
                        cursosList.add(curso);
                    }
                    CursoAdapter adapter = new CursoAdapter(getContext(),cursosList);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogMenu(view);
                        }
                    });

                    rvCursos.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void dialogMenu(View view) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView( com.acevedo.educonnect.commonresources.R.layout.dialog_menu_curso);


        //datos del item seleccionado
        int id = cursosList.get(rvCursos.getChildAdapterPosition(view)).getId();


        CardView cvTareas = dialog.findViewById(com.acevedo.educonnect.commonresources.R.id.cvTareas);
        CardView cvAsistencia = dialog.findViewById(com.acevedo.educonnect.commonresources.R.id.cvAsistencia);

        cvTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(getContext(), ListTareasActivity.class);
                i.putExtra("id_curso",id);
                startActivity(i);
            }
        });

        cvAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(getContext(), RegistrarAsistenciaActivity.class);
                i.putExtra("id_curso",id);
                startActivity(i);
            }
        });



        dialog.show();
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = com.acevedo.educonnect.commonresources.R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}