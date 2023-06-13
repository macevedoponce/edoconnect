package com.acevedo.educonnect.estudiante.ui.cursos.trabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acevedo.educonnect.estudiante.R;

public class EnviarTrabajoActivity extends AppCompatActivity {

    LinearLayout llRegresar;
    TextView tvTitTarea,tvDescTarea, tvFLTarea;
    CardView cvTomarFoto, cvSubirPDF;
    int idTarea, idCurso;
    String titTarea, descTarea, flTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_trabajo);
        llRegresar = findViewById(R.id.llRegresar);
        tvTitTarea = findViewById(R.id.tvTitTarea);
        tvDescTarea = findViewById(R.id.tvDescTarea);
        tvFLTarea = findViewById(R.id.tvFLTarea);
        cvTomarFoto = findViewById(R.id.cvTomarFoto);
        cvSubirPDF = findViewById(R.id.cvSubirPDF);
        recepcionarDatos();

        llRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void recepcionarDatos() {
        idTarea = getIntent().getIntExtra("id_tarea",0);
        idCurso = getIntent().getIntExtra("id_curso",0);
        titTarea = getIntent().getStringExtra("titulo_tarea");
        descTarea = getIntent().getStringExtra("descripcion_tarea");
        flTarea = getIntent().getStringExtra("fecha_limite_tarea");

        //pintar datos
        tvTitTarea.setText(titTarea);
        tvDescTarea.setText(descTarea);
        tvFLTarea.setText(flTarea);

        cvTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnviarTrabajoActivity.this, EnviarFotoActivity.class);
                intent.putExtra("idTarea", idTarea);
                intent.putExtra("idCurso", idCurso);
                startActivity(intent);
            }
        });

        cvSubirPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnviarTrabajoActivity.this, EnviarPdfActivity.class);
                intent.putExtra("idTarea", idTarea);
                intent.putExtra("idCurso", idCurso);
                startActivity(intent);
            }
        });

    }
}