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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.acevedo.educonnect.Clases.Meses;
import com.acevedo.educonnect.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CrearTareaActivity extends AppCompatActivity {

    LinearLayout llRegresar;
    CardView cvCrearTarea,cvFechaLimiteEntrega;
    TextView tvFechaLimiteEntrega,tvFLE;
    TextInputLayout tiltitulo,tilDescripcion;
    TextInputEditText edtTitulo,edtDescripcion;

    ToggleButton tbMictitulo, tbMicDescripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);
        llRegresar = findViewById(R.id.llRegresar);
        cvCrearTarea = findViewById(R.id.cvCrearTarea);
        cvFechaLimiteEntrega = findViewById(R.id.cvFechaLimiteEntrega);
        tvFechaLimiteEntrega = findViewById(R.id.tvFechaLimiteEntrega);
        tvFLE = findViewById(R.id.tvFLE);
        tiltitulo = findViewById(R.id.tiltitulo);
        tilDescripcion = findViewById(R.id.tilDescripcion);
        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        tbMictitulo = findViewById(R.id.tbMictitulo);
        tbMicDescripcion = findViewById(R.id.tbMicDescripcion);

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
                crearTarea();
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
                String fechaSeleccionada;

                if(mes.getValue() != 0){
                    fechaSeleccionada = anio.getValue()+"-"+ mes.getValue()+"-"+dia.getValue();
                    tvFLE.setVisibility(View.VISIBLE);
                    tvFechaLimiteEntrega.setText(fechaSeleccionada);
                    tvFechaLimiteEntrega.setTextSize(20);
                    cvFechaLimiteEntrega.setCardBackgroundColor(getResources().getColor(R.color.selected));
                    tvFechaLimiteEntrega.setTextColor(getResources().getColor(R.color.primary));
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
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void crearTarea() {
        int id_curso = getIntent().getIntExtra("id_curso",0);
        String titulo = edtTitulo.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
    }
}