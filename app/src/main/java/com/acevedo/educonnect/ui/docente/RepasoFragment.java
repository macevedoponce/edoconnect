package com.acevedo.educonnect.ui.docente;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.acevedo.educonnect.Adapters.ExamenAdapter;
import com.acevedo.educonnect.Adapters.PreguntaAdapter;
import com.acevedo.educonnect.Clases.Alternativas;
import com.acevedo.educonnect.Clases.Examen;
import com.acevedo.educonnect.Clases.Preguntas;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.Util.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RepasoFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    FloatingActionButton fabAgregarExamen;
    ProgressDialog progreso;
    RequestQueue requestQueue;
    List<Examen> listaExamen;

    JsonObjectRequest jsonObjectRequest;
    RecyclerView rvExamen;

    private SpeechRecognizer speechRecognizer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista =  inflater.inflate(R.layout.fragment_repaso, container, false);
        fabAgregarExamen = vista.findViewById(R.id.fabAgregarExamen);

        // Inicializar la lista de carreras y el RecyclerView
        rvExamen = vista.findViewById(R.id.rvExamen);
        listaExamen = new ArrayList<>();
        rvExamen.setLayoutManager(new LinearLayoutManager(getContext()));
        rvExamen.setHasFixedSize(true);
        fabAgregarExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el AlertDialog y establecer su contenido
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_crear_examen, null);
                builder.setView(dialogView);

                // Mostrar el AlertDialog
                AlertDialog dialog = builder.create();
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                ToggleButton micCrearExamen = dialogView.findViewById(R.id.tbMicCrearExamen);
                CardView cvSiguiente = dialogView.findViewById(R.id.cvCrearExamen);
                TextInputEditText titExamen = dialogView.findViewById(R.id.edtNuevoExamen);
                TextInputEditText descExamen = dialogView.findViewById(R.id.edtNuevoDescrpExamen);
                micCrearExamen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                cvSiguiente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String tituloExamen = titExamen.getText().toString();
                        String descipExamen = descExamen.getText().toString();
                        Intent intent = new Intent(getContext(), AgregarPreguntaExamen.class);
                        intent.putExtra("tituloExamen", tituloExamen);
                        intent.putExtra("descripcionExamen", descipExamen);
                        startActivity(intent);

                    }
                });

            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        listasExamen() ;
        return vista;
    }
    private void listasExamen() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Buscando Examen");
        progreso.show();
        String url = Util.RUTA + "/list_examen.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Examen examen = null;
        progreso.hide();
        JSONArray json = response.optJSONArray("examen");

        try {
            for(int i=0; i<json.length();i++){
                examen = new Examen();

                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                examen.setNombreExamen(jsonObject.getString("nombre"));



                listaExamen.add(examen);
            }
            ExamenAdapter adapter = new ExamenAdapter(getContext(),listaExamen);
            rvExamen.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "Error en Volley", Toast.LENGTH_SHORT).show();
    }


}