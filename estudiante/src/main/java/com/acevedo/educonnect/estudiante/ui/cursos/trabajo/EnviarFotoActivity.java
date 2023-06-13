package com.acevedo.educonnect.estudiante.ui.cursos.trabajo;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.acevedo.educonnect.commonresources.Util.Util;
import com.acevedo.educonnect.estudiante.R;
import com.acevedo.educonnect.estudiante.ui.cursos.ListTareasActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EnviarFotoActivity extends AppCompatActivity {

    LinearLayout llRegresar;
    CardView cvTomarFoto, cvSubirTarea;
    ImageView ivFotoTarea;

    RequestQueue requestQueue;
    StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_foto);
        llRegresar = findViewById(R.id.llRegresar);
        cvTomarFoto = findViewById(R.id.cvTomarFoto);
        cvSubirTarea = findViewById(R.id.cvSubirTarea);
        ivFotoTarea = findViewById(R.id.ivFotoTarea);
        requestQueue = Volley.newRequestQueue(this);

        llRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cvTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarImagen();
            }
        });


        cvSubirTarea.setVisibility(View.GONE);

        cvSubirTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirTarea();
            }
        });
    }

    private void seleccionarImagen() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                ivFotoTarea.setImageURI(selectedImageUri);
                cvSubirTarea.setVisibility(View.VISIBLE);
            }
        }
    }

    private void subirTarea() {
        Bitmap bitmap =  ((BitmapDrawable) ivFotoTarea.getDrawable()).getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

        SharedPreferences preferences = getSharedPreferences("usuarioLoginEstudiante", Context.MODE_PRIVATE);
        int idEstudiante = preferences.getInt("id",0);
        int idTarea = getIntent().getIntExtra("idTarea",0);
        int idCurso = getIntent().getIntExtra("idCurso",0);

        String url = Util.RUTA_REGISTRAR_TAREA;

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(EnviarFotoActivity.this, ListTareasActivity.class);
                intent.putExtra("id_curso",idCurso);
                startActivity(intent);
                finish();
                Toast.makeText(EnviarFotoActivity.this, "Tarea enviada", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EnviarFotoActivity.this, "error BD", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idEstudiante",idEstudiante+"");
                parametros.put("idTarea",idTarea+"");
                parametros.put("tipoTarea","1");
                parametros.put("file",fotoEnBase64);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

}