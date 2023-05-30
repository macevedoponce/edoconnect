package com.acevedo.educonnect.ui.docente.curso.tarea;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.acevedo.educonnect.Clases.Meses;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.commonresources.Util.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CrearTareaActivity extends AppCompatActivity {

    LinearLayout llRegresar;
    CardView cvCrearTarea,cvFechaLimiteEntrega;
    TextView tvFechaLimiteEntrega,tvFLE,tvTitleActivity,tvButtonName;
    TextInputLayout tiltitulo,tilDescripcion;
    TextInputEditText edtTitulo,edtDescripcion;
    ToggleButton tbMictitulo, tbMicDescripcion;
    String fechaSeleccionada = "";
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);
        tvTitleActivity = findViewById(R.id.tvTitleActivity);
        tvButtonName = findViewById(R.id.tvButtonName);
        llRegresar = findViewById(R.id.llRegresar);
        cvCrearTarea = findViewById(R.id.cvCrearTarea);
        requestQueue = Volley.newRequestQueue(this);
        cvFechaLimiteEntrega = findViewById(R.id.cvFechaLimiteEntrega);
        tvFechaLimiteEntrega = findViewById(R.id.tvFechaLimiteEntrega);
        tvFLE = findViewById(R.id.tvFLE);
        tiltitulo = findViewById(R.id.tiltitulo);
        tilDescripcion = findViewById(R.id.tilDescripcion);
        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        tbMictitulo = findViewById(R.id.tbMictitulo);
        tbMicDescripcion = findViewById(R.id.tbMicDescripcion);

        //obtener datos
        //validar si hay datos para actualizar
        int id_r = getIntent().getIntExtra("id",0);
        String titulo_r = getIntent().getStringExtra("titulo");
        String descripcion_r = getIntent().getStringExtra("descripcion");
        String fecha_limite_r = getIntent().getStringExtra("fecha_limite");

        if(id_r != 0){
            tvTitleActivity.setText("Editar Tarea");
            tvButtonName.setText("Editar Tarea");
            edtTitulo.setText(titulo_r);
            edtDescripcion.setText(descripcion_r);
            fechaSeleccionada = fecha_limite_r;
            tvFechaLimiteEntrega.setText(fecha_limite_r);
        }

        tbMictitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn = "titulo";
                iniciarDeteccion(btn);
            }
        });

        tbMicDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn = "descripcion";
                iniciarDeteccion(btn);
            }
        });

        llRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cvCrearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_r != 0){
                    actualizarTarea(id_r);
                }else{
                    crearTarea();
                }

            }
        });

        cvFechaLimiteEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                showDialogFecha(year,month,day);
            }
        });

    }

    private void actualizarTarea(int id_r) {
        String url = Util.RUTA_ACTUALIZAR_TAREA;
        String titulo = edtTitulo.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        if(!titulo.isEmpty()) {
            if(!descripcion.isEmpty()) {
                if(fechaSeleccionada != null && fechaSeleccionada.length() > 0) {
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialogRegistroExitoso(titulo,descripcion,fechaSeleccionada,response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CrearTareaActivity.this, "error " + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("titulo", titulo);
                            params.put("descripcion", descripcion);
                            params.put("fecha_limite", fechaSeleccionada);
                            params.put("id_tarea", String.valueOf(id_r));
                            return params;
                        }
                    };
                    requestQueue.add(request);
                }else{
                    tvFechaLimiteEntrega.setText("Seleccione una fecha limite para la tarea");
                    tvFechaLimiteEntrega.setTextColor(Color.RED);
                }

            }else{
                tilDescripcion.setError("Ingrese descripcion");
            }
        }else{
            tiltitulo.setError("Ingrese titulo");
        }
    }

    private void iniciarDeteccion(String btn) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); // lenguaje por defecto del celular
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora");
        try {
            if(btn.equals("titulo")){
                startActivityForResult(i, 100); // pueden tener varios resultados
            }else{
                startActivityForResult(i, 101); // pueden tener varios resultados
            }

        }catch (ActivityNotFoundException e){
            Toast.makeText(CrearTareaActivity.this, "Error al Reconocer", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if(resultCode == RESULT_OK && data != null ){
                    ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edtTitulo.setText(resultado.get(0));
                }
                break;
            case 101:
                if(resultCode == RESULT_OK && data != null ){
                    ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edtDescripcion.setText(resultado.get(0));
                }
                break;

        }
    }


    private void showDialogFecha(int year, int month, int day) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fecha);

        NumberPicker dia = dialog.findViewById(R.id.day);
        NumberPicker mes = dialog.findViewById(R.id.month);
        NumberPicker anio = dialog.findViewById(R.id.year);
        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        CardView cvAceptar = dialog.findViewById(R.id.cvAceptar);

        Meses.initMeses();

        dia.setMaxValue(31);
        mes.setMaxValue(Meses.getMesesArrayList().size()-1);
        mes.setDisplayedValues(Meses.mesesNames());
        anio.setMaxValue(year);

        dia.setMinValue(1);
        mes.setMinValue(0);
        anio.setMinValue(year-70);

        dia.setValue(day);
        mes.setValue(month+1);
        anio.setValue(year);


        cvAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mes.getValue() != 0){
                    fechaSeleccionada = anio.getValue()+"-"+ mes.getValue()+"-"+dia.getValue();
                    tvFLE.setVisibility(View.VISIBLE);
                    tvFechaLimiteEntrega.setText(fechaSeleccionada);
                    tvFechaLimiteEntrega.setTextSize(20);
                    cvFechaLimiteEntrega.setCardBackgroundColor(getResources().getColor(com.acevedo.educonnect.commonresources.R.color.selected));
                    tvFechaLimiteEntrega.setTextColor(getResources().getColor(com.acevedo.educonnect.commonresources.R.color.primary));
                    dialog.dismiss();
                }

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = com.acevedo.educonnect.commonresources.R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void crearTarea() {
        String url = Util.RUTA_CREAR_TAREA;
        int id_curso = getIntent().getIntExtra("id_curso",0);
        String titulo = edtTitulo.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        if(!titulo.isEmpty()) {
            if(!descripcion.isEmpty()) {
                if(fechaSeleccionada != null && fechaSeleccionada.length() > 0) {
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialogRegistroExitoso(titulo,descripcion,fechaSeleccionada,response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CrearTareaActivity.this, "error " + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("titulo", titulo);
                            params.put("descripcion", descripcion);
                            params.put("fecha_limite", fechaSeleccionada);
                            params.put("estado_id", String.valueOf(1));
                            params.put("curso_id", String.valueOf(id_curso));
                            return params;
                        }
                    };
                    requestQueue.add(request);
                }else{
                    tvFechaLimiteEntrega.setText("Seleccione una fecha limite para la tarea");
                    tvFechaLimiteEntrega.setTextColor(Color.RED);
                }

            }else{
                tilDescripcion.setError("Ingrese descripcion");
            }
        }else{
            tiltitulo.setError("Ingrese titulo");
        }
    }

    private void dialogRegistroExitoso(String titulo, String descripcion, String fechaLimite, String response) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_registro_tarea_exitoso);

        TextView tvRespuestaServidor = dialog.findViewById(R.id.tvRespuestaServidor);
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
        dialog.getWindow().getAttributes().windowAnimations = com.acevedo.educonnect.commonresources.R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}