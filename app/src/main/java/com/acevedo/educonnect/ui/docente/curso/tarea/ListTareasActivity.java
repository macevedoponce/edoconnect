package com.acevedo.educonnect.ui.docente.curso.tarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acevedo.educonnect.Adapters.TareaAdapter;
import com.acevedo.educonnect.Clases.Tarea;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.Util.Util;
import com.acevedo.educonnect.ui.docente.curso.entregaTarea.ListEntregaTareasActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListTareasActivity extends AppCompatActivity {

    SwipeRefreshLayout srlActualizarTareas;
    LinearLayout llRegresar;
    FloatingActionButton fabAgregarTarea;
    RecyclerView rvTareas;
    RequestQueue requestQueue;
    List<Tarea> tareaList;

    int id_curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tareas);
        srlActualizarTareas = findViewById(R.id.srlActualizarTareas);
        llRegresar = findViewById(R.id.llRegresar);
        fabAgregarTarea = findViewById(R.id.fabAgregarTarea);
        rvTareas = findViewById(R.id.rvTareas);
        rvTareas.setHasFixedSize(true);
        rvTareas.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        tareaList = new ArrayList<>();
        cargarTareas();

        srlActualizarTareas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tareaList.clear();
                cargarTareas();
                srlActualizarTareas.setRefreshing(false);
            }
        });

        llRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fabAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListTareasActivity.this,CrearTareaActivity.class);
                intent.putExtra("id_curso",id_curso);
                startActivity(intent);
            }
        });
    }

    private void cargarTareas() {
        id_curso = getIntent().getIntExtra("id_curso",0);
        String url = Util.RUTA_TAREAS+"?id_curso="+id_curso;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tareas");
                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id =jsonObject.getInt("id");
                        String titulo = jsonObject.getString("titulo");
                        String descripcion =jsonObject.getString("descripcion");
                        String fecha_limite =jsonObject.getString("fecha_limite");
                        int estado =jsonObject.getInt("estado");
                        int curso_id = jsonObject.getInt("curso_id");
                        Tarea tarea = new Tarea(id,titulo,descripcion,fecha_limite,estado,curso_id);
                        tareaList.add(tarea);
                    }
                    TareaAdapter adapter = new TareaAdapter(ListTareasActivity.this,tareaList);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogMenu(view);
                        }
                    });

                    rvTareas.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListTareasActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void dialogMenu(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_menu_tarea);


        //datos del item seleccionado
        int id = tareaList.get(rvTareas.getChildAdapterPosition(view)).getId();
        String titulo = tareaList.get(rvTareas.getChildAdapterPosition(view)).getTitulo();
        String descripcion = tareaList.get(rvTareas.getChildAdapterPosition(view)).getDescripcion();
        String fecha_limite = tareaList.get(rvTareas.getChildAdapterPosition(view)).getFecha_limite();
        int estado = tareaList.get(rvTareas.getChildAdapterPosition(view)).getEstado();


        CardView cvVerEntregables = dialog.findViewById(R.id.cvVerEntregables);
        CardView cvVistaPrevia = dialog.findViewById(R.id.cvVistaPrevia);
        CardView cvEditarTarea = dialog.findViewById(R.id.cvEditarTarea);

        cvVerEntregables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(ListTareasActivity.this, ListEntregaTareasActivity.class);
                i.putExtra("titulo", titulo);
                i.putExtra("id_tarea", id);
                startActivity(i);
            }
        });

        cvVistaPrevia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                vistaPrevia(titulo,descripcion,fecha_limite);
            }
        });

        cvEditarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListTareasActivity.this, CrearTareaActivity.class);
                i.putExtra("titulo",titulo);
                i.putExtra("descripcion",descripcion);
                i.putExtra("fecha_limite",fecha_limite);
                i.putExtra("id",id);
                startActivity(i);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private void vistaPrevia(String titulo, String descripcion,String fecha_limite) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_detalle_tarea);

        TextView tvTituloTarea = dialog.findViewById(R.id.tvTituloTarea);
        TextView tvFechaLimiteTarea = dialog.findViewById(R.id.tvFechaLimiteTarea);
        TextView tvDescripcionTarea = dialog.findViewById(R.id.tvDescripcionTarea);
        ImageView ivCloseDialogDetalleTarea = dialog.findViewById(R.id.ivCloseDialogDetalleTarea);


        tvTituloTarea.setText(titulo);
        tvFechaLimiteTarea.setText(fecha_limite);
        tvDescripcionTarea.setText(descripcion);


        ivCloseDialogDetalleTarea.setOnClickListener(new View.OnClickListener() {
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
}