package com.acevedo.educonnect.ui.docente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.acevedo.educonnect.Clases.Examen;
import com.acevedo.educonnect.R;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AgregarPreguntaExamen extends AppCompatActivity {

    ProgressDialog progreso;
    RequestQueue requestQueue;
    List<Examen> listaExamen;

    JsonObjectRequest jsonObjectRequest;
    RecyclerView rvPreguntasElegir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pregunta_examen);

        Intent intent = getIntent();
        String tituloExamen = intent.getStringExtra("tituloExamen");
        String descripExamen = intent.getStringExtra("descripcionExamen");
        Toast.makeText(this, tituloExamen+" "+descripExamen , Toast.LENGTH_SHORT).show();


        // Inicializar la lista de carreras y el RecyclerView
        rvPreguntasElegir = findViewById(R.id.rvPreguntasElegir);
        listaExamen = new ArrayList<>();
        rvPreguntasElegir.setLayoutManager(new LinearLayoutManager(this));
        rvPreguntasElegir.setHasFixedSize(true);

    }
}