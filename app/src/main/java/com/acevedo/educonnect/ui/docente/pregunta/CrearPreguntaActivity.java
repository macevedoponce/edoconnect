package com.acevedo.educonnect.ui.docente.pregunta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.acevedo.educonnect.R;
import com.acevedo.educonnect.commonresources.Util.Util;
import com.acevedo.educonnect.ui.docente.PreguntasFragment;
import com.acevedo.educonnect.ui.docente.curso.entregaTarea.DetalleEntregaTareaActivity;
import com.acevedo.educonnect.ui.docente.curso.entregaTarea.FullScreenTareaActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CrearPreguntaActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText pregunta,respuestaCorrecta,respuesIncorrecta1,respuestaIncorrecta2,respuestaIncorrecta3,retroalimentacion;
    CardView btncrearPregunta, cvMenos, cvMas;
    TextView txtPuntaje;
    ToggleButton micPregunta,micrespuestaCorrecta,micRespuesIncorrecta1,micRespuestaIncorrecta2,micRespuestaIncorrecta3,micRetroalimentacion;
    LinearLayout volver;
    int botonID = -1;
    int puntaje_pregunta = 0;

    RequestQueue requestQueue;

    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pregunta);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        btncrearPregunta= findViewById(R.id.cvCrearPregunta);

        volver= findViewById(R.id.llRegresarPregunta);

        pregunta = findViewById(R.id.edtPregunta);
        respuestaCorrecta = findViewById(R.id.edtAlterCorrecta);
        respuesIncorrecta1 = findViewById(R.id.edtalterIncorrecta1);
        respuestaIncorrecta2 = findViewById(R.id.edtalterIncorrecta2);
        respuestaIncorrecta3 = findViewById(R.id.edtalterIncorrecta3);
        retroalimentacion = findViewById(R.id.edtRetroalimentacion);
        cvMas = findViewById(R.id.cvMas);
        cvMenos = findViewById(R.id.cvMenos);
        txtPuntaje = findViewById(R.id.txtPuntajePregunta);

        micPregunta = findViewById(R.id.tbMicPregunta);
        micrespuestaCorrecta = findViewById(R.id.tbMicalterCorrecta);
        micRespuesIncorrecta1 = findViewById(R.id.tbMicAlterIncorrecta);
        micRespuestaIncorrecta2 = findViewById(R.id.tbMicAlterIncorrecta2);
        micRespuestaIncorrecta3 = findViewById(R.id.tbMicAlterIncorrecta3);
        micRetroalimentacion = findViewById(R.id.tbMicretroalimentacion);

        cvMas.setOnClickListener(this);
        cvMenos.setOnClickListener(this);

        micPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonID = R.id.tbMicPregunta;
                iniciarDeteccion();
            }
        });
        micrespuestaCorrecta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonID = R.id.tbMicalterCorrecta;
                iniciarDeteccion();
            }
        });
        micRespuesIncorrecta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonID = R.id.tbMicAlterIncorrecta;
                iniciarDeteccion();
            }
        });
        micRespuestaIncorrecta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonID = R.id.tbMicAlterIncorrecta2;
                iniciarDeteccion();
            }
        });
        micRespuestaIncorrecta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonID = R.id.tbMicAlterIncorrecta3;
                iniciarDeteccion();
            }
        });
        micRetroalimentacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonID = R.id.tbMicretroalimentacion;
                iniciarDeteccion();
            }
        });


        btncrearPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pregunta = pregunta.getText().toString();
                String altercorrecta = respuestaCorrecta.getText().toString();
                String alterincorrecta1 = respuesIncorrecta1.getText().toString();
                String alterincorrecta2 = respuestaIncorrecta2.getText().toString();
                String alterincorrecta3 = respuestaIncorrecta3.getText().toString();
                String Retroalimentacion = retroalimentacion.getText().toString();
                String puntaje = txtPuntaje.getText().toString();


                if(     Pregunta.isEmpty() ||
                        altercorrecta.isEmpty() ||
                        alterincorrecta1.isEmpty() ||
                        alterincorrecta2.isEmpty() ||
                        alterincorrecta3.isEmpty() ||
                        Retroalimentacion.isEmpty()
                ){
                    Toast.makeText(CrearPreguntaActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    InsertarPregunta(Pregunta,altercorrecta,alterincorrecta1,alterincorrecta2,alterincorrecta3,Retroalimentacion,puntaje);
                }

            }
        });
    }



    private void iniciarDeteccion() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); // lenguaje por defecto del celular
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora");
        try {
            startActivityForResult(i, 100); // pueden tener varios resultados
        }catch (ActivityNotFoundException e){
            Toast.makeText(CrearPreguntaActivity.this, "Error al Reconocer", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null ){
            if (botonID == R.id.tbMicPregunta) {
                ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                pregunta.setText(resultado.get(0));
            }else if (botonID == R.id.tbMicalterCorrecta) {
                ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                respuestaCorrecta.setText(resultado.get(0));
            }else if (botonID == R.id.tbMicAlterIncorrecta) {
                ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                respuesIncorrecta1.setText(resultado.get(0));
            }else if (botonID == R.id.tbMicAlterIncorrecta2) {
                ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                respuestaIncorrecta2.setText(resultado.get(0));
            }else if (botonID == R.id.tbMicAlterIncorrecta3) {
                ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                respuestaIncorrecta3.setText(resultado.get(0));
            }else if (botonID == R.id.tbMicretroalimentacion) {
                ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                retroalimentacion.setText(resultado.get(0));
            }
        }

    }
    private void InsertarPregunta(String Pregunta, String altercorrecta, String alterincorrecta1, String alterincorrecta2, String alterincorrecta3, String Retroalimentacion, String puntaje) {
        ProgressDialog progreso = new ProgressDialog(this);
        progreso.setMessage("Registrando");
        progreso.show();

        // URL de la API PHP
        String url = Util.RUTA+"/add_pregunta_alternativas.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.dismiss();

//                Intent intent = new Intent(CrearPreguntaActivity.this, PreguntasFragment.class);
//                Toast.makeText(CrearPreguntaActivity.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
//                startActivity(intent);
                onBackPressed();
                Toast.makeText(CrearPreguntaActivity.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearPreguntaActivity.this, "error " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pregunta_nombre", Pregunta);
                params.put("pregunta_retroalimentacion", Retroalimentacion);
                params.put("pregunta_puntaje", puntaje);
                params.put("alternativa_correcta", altercorrecta);
                params.put("alternativa_incorrecta1", alterincorrecta1);
                params.put("alternativa_incorrecta2", alterincorrecta2);
                params.put("alternativa_incorrecta3", alterincorrecta3);
                return params;
            }
        };
        requestQueue.add(request);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cvMas:
                puntaje_pregunta++;

                break;
            case R.id.cvMenos:
                puntaje_pregunta--;
                break;
        }

        if(puntaje_pregunta >= 1 && puntaje_pregunta <=5){
            txtPuntaje.setText(puntaje_pregunta+"");
        }else{
            if (puntaje_pregunta<=1){
                puntaje_pregunta = 1;
                txtPuntaje.setText(puntaje_pregunta+"");
            }

            if (puntaje_pregunta>=5){
                puntaje_pregunta = 5;
                txtPuntaje.setText(puntaje_pregunta+"");
            }

        }
    }
}