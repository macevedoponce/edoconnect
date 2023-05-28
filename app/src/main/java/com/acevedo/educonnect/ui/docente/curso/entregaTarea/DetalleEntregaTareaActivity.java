package com.acevedo.educonnect.ui.docente.curso.entregaTarea;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.acevedo.educonnect.R;
import com.acevedo.educonnect.Util.Util;
import com.acevedo.educonnect.ui.docente.curso.tarea.CrearTareaActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DetalleEntregaTareaActivity extends AppCompatActivity implements View.OnClickListener {
    int id_entrega,nota;
    LinearLayout llRegresar;
    String nombres, apellidos, retroalimentaicon,url_trabajo;

    TextView tvEstudiante,tvNota;
    CardView cvFullScreen,cvMas,cvMenos,cvGuardarNota;
    ImageView ivContenido;
    ToggleButton tbMicRetroalimenacion;
    RequestQueue requestQueue;

    int nota_estudiante = 0;

    TextInputEditText edtRetroalimentacion;
    TextInputLayout tilRetroalimentacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_entrega_tarea);
        llRegresar = findViewById(R.id.llRegresar);
        tvEstudiante = findViewById(R.id.tvEstudiante);
        tvNota = findViewById(R.id.tvNota);
        cvFullScreen = findViewById(R.id.cvFullScreen);
        cvMenos = findViewById(R.id.cvMenos);
        cvMas = findViewById(R.id.cvMas);
        cvGuardarNota = findViewById(R.id.cvGuardarNota);
        ivContenido = findViewById(R.id.ivContenido);
        requestQueue = Volley.newRequestQueue(this);
        edtRetroalimentacion = findViewById(R.id.edtRetroalimentacion);
        tbMicRetroalimenacion = findViewById(R.id.tbMicRetroalimenacion);
        tilRetroalimentacion = findViewById(R.id.tilRetroalimentacion);

        obtenerDatos();
        cargarDatos();

        cvMas.setOnClickListener(this);
        cvMenos.setOnClickListener(this);
        cvFullScreen.setOnClickListener(this);
        cvGuardarNota.setOnClickListener(this);
        tbMicRetroalimenacion.setOnClickListener(this);
        llRegresar.setOnClickListener(this);


    }

    private void cargarDatos() {
        tvEstudiante.setText(nombres + " " + apellidos);
        nota_estudiante = nota;
        tvNota.setText(nota_estudiante+"");
        tvEstudiante.setText(nombres + " " + apellidos);
        if(!retroalimentaicon.isEmpty() && retroalimentaicon.length()>=5){
            edtRetroalimentacion.setText(retroalimentaicon);
        }else{
            edtRetroalimentacion.setText("");
        }
        Glide.with(this).load(url_trabajo).into(ivContenido);
    }

    private void obtenerDatos() {

        id_entrega = getIntent().getIntExtra("id_entrega",0);
        nota = getIntent().getIntExtra("nota",0);
        nombres = getIntent().getStringExtra("nombres");
        apellidos = getIntent().getStringExtra("apellidos");
        retroalimentaicon = getIntent().getStringExtra("retroalimentaicon");
        url_trabajo = getIntent().getStringExtra("url_trabajo");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cvMas:
                nota_estudiante++;

                break;
            case R.id.cvMenos:
                nota_estudiante--;
                break;
            case R.id.cvFullScreen:
                Toast.makeText(this, "pantalla completa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cvGuardarNota:
                guardarNota();
                break;
            case R.id.tbMicRetroalimenacion:
                iniciarDeteccion();
                break;
            case R.id.llRegresar:
                onBackPressed();
                break;
        }

        if(nota_estudiante >= 1 && nota_estudiante <=20){
            tvNota.setText(nota_estudiante+"");
        }else{
            if (nota_estudiante<=0){
                nota_estudiante = 0;
                tvNota.setText(nota_estudiante+"");
            }

            if (nota_estudiante>=20){
                nota_estudiante = 20;
                tvNota.setText(nota_estudiante+"");
            }

        }
    }

    private void guardarNota() {

        String retroalimentacion_campo = edtRetroalimentacion.getText().toString();
        int nota_campo = Integer.parseInt(tvNota.getText().toString());

        if(!retroalimentacion_campo.isEmpty() && retroalimentacion_campo.length() >= 6){
            if(nota_campo != 0){
                String url = Util.RUTA_CALIFICAR_TAREA;
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    dialogRegistroExitoso(tvEstudiante.getText().toString(),retroalimentacion_campo,nota_campo+"",response);
                        //Toast.makeText(DetalleEntregaTareaActivity.this, "registrado exitosamente", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetalleEntregaTareaActivity.this, "error " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_entrega", String.valueOf(id_entrega));
                        params.put("retroalimentacion", retroalimentacion_campo);
                        params.put("nota", String.valueOf(nota_campo));
                        return params;
                    }
                };
                requestQueue.add(request);
            }else{
                Toast.makeText(this, "Necesita registrar la nota", Toast.LENGTH_SHORT).show();
            }

        }else{
            tilRetroalimentacion.setError("Necesita brindar retroalimentación al estudiante");
        }


    }

    private void dialogRegistroExitoso(String titulo, String descripcion, String fechaLimite, String response) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_registro_tarea_exitoso);

        TextView tvRespuestaServidor = dialog.findViewById(R.id.tvRespuestaServidor);
        TextView tvDecorationFL = dialog.findViewById(R.id.tvDecorationFL);
        TextView tvTitulo = dialog.findViewById(R.id.tvTitulo);
        TextView tvDescripcion = dialog.findViewById(R.id.tvDescripcion);
        TextView tvFechaLimite = dialog.findViewById(R.id.tvFechaLimite);
        CardView cvAceptar = dialog.findViewById(R.id.cvAceptar);

        try {
            JSONObject response_api = new JSONObject(response);
            String message = response_api.getString("message");
            tvRespuestaServidor.setText(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvDecorationFL.setText("Nota: ");
        tvTitulo.setText(titulo);
        tvDescripcion.setText(descripcion);
        tvFechaLimite.setText(fechaLimite);

        cvAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });


        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private void iniciarDeteccion() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); // lenguaje por defecto del celular
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora");
        try {
            startActivityForResult(i, 100); // pueden tener varios resultados

        }catch (ActivityNotFoundException e){
            Toast.makeText(DetalleEntregaTareaActivity.this, "Error al Reconocer", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if(resultCode == RESULT_OK && data != null ){
                    ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(this, resultado.get(0) + "", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}