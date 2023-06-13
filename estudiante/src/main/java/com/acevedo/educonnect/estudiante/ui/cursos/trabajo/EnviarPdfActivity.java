package com.acevedo.educonnect.estudiante.ui.cursos.trabajo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.acevedo.educonnect.commonresources.Util.Util;
import com.acevedo.educonnect.estudiante.R;
import com.acevedo.educonnect.estudiante.ui.cursos.ListTareasActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EnviarPdfActivity extends AppCompatActivity {
    CardView cvSeleccionarDocumento;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_pdf);
        cvSeleccionarDocumento = findViewById(R.id.cvSeleccionarDocumento);
        requestQueue = Volley.newRequestQueue(this);



        cvSeleccionarDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre el explorador de archivos para seleccionar un PDF
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Selecciona un archivo PDF"), 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri pdfUri = data.getData();
                InputStream pdfInputStream = null;
                try {
                    pdfInputStream = getContentResolver().openInputStream(pdfUri);
                    if (pdfInputStream != null) {
                        //mostrar vista de pdf
                        uploadPdfToServer(pdfInputStream);
                    } else {
                        Toast.makeText(this, "No se pudo obtener el archivo PDF", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al abrir el archivo PDF", Toast.LENGTH_SHORT).show();
                } finally {
                    if (pdfInputStream != null) {
                        try {
                            pdfInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "No se seleccionó ningún archivo PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPdfToServer(InputStream pdfInputStream) {
        String url = Util.RUTA_REGISTRAR_TAREA;
        SharedPreferences preferences = getSharedPreferences("usuarioLoginEstudiante", Context.MODE_PRIVATE);
        int idEstudiante = preferences.getInt("id",0);
        int idTarea = getIntent().getIntExtra("idTarea",0);
        int idCurso = getIntent().getIntExtra("idCurso",0);

        try {
            // Leer el contenido del PDF y convertirlo a Base64
            byte[] pdfData = convertInputStreamToByteArray(pdfInputStream);
            String pdfBase64 = Base64.encodeToString(pdfData, Base64.DEFAULT);


            // Crear una solicitud POST con Volley
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent intent = new Intent(EnviarPdfActivity.this, ListTareasActivity.class);
                            intent.putExtra("id_curso",idCurso);
                            startActivity(intent);
                            finish();
                            Toast.makeText(EnviarPdfActivity.this, "Tarea enviada", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Manejar el error de la solicitud
                            Toast.makeText(EnviarPdfActivity.this, "Error al subir el archivo PDF", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    // Agregar los parámetros adicionales
                    Map<String, String> params = new HashMap<>();
                    params.put("idEstudiante", idEstudiante+"");
                    params.put("idTarea", idTarea+"");
                    params.put("tipoTarea", "2");
                    params.put("file", pdfBase64); // Enviar el contenido del archivo PDF como una cadena Base64
                    return params;
                }
            };

            // Agregar la solicitud a la cola de Volley
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bytesRead;
        byte[] data = new byte[4096];
        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

}