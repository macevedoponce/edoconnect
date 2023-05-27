package com.acevedo.educonnect.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.Clases.Curso;
import com.acevedo.educonnect.Clases.Tarea;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaHolder> implements View.OnClickListener {

    Context context;
    List<Tarea> tareaList;
    View.OnClickListener listener;


    public TareaAdapter(Context context, List<Tarea> tareaList) {
        this.context = context;
        this.tareaList = tareaList;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public TareaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.tarea_item, parent,false);
        mView.setOnClickListener(this);
        return new TareaHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaAdapter.TareaHolder holder, int position) {
        Tarea tarea = tareaList.get(position);
        String titulo = tarea.getTitulo();
        int id_tarea = tarea.getId();
        int estado = tarea.getEstado();

        holder.setTitulo(titulo);
        holder.setEstado(estado);
        holder.tbEstadoTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int estado_boton;
                if(holder.tbEstadoTarea.isChecked()){
                    estado_boton = 1;
                }else{
                    estado_boton = 2;
                }

                holder.actualizarEstado(id_tarea, estado_boton);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareaList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }

    public class TareaHolder extends RecyclerView.ViewHolder {

        TextView tvTituloTarea;
        //RadioButton rbEstadoTarea;
        ToggleButton tbEstadoTarea;
        View view;

        RequestQueue requestQueue;
        public TareaHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvTituloTarea = view.findViewById(R.id.tvTituloTarea);
            //rbEstadoTarea = view.findViewById(R.id.rbEstadoTarea);
            tbEstadoTarea = view.findViewById(R.id.tbEstadoTarea);
            requestQueue = Volley.newRequestQueue(context);
        }

        public void setTitulo(String titulo) {
            tvTituloTarea.setText(titulo);
        }


        public void setEstado(int estado) {
            if(estado == 1){
                tbEstadoTarea.setChecked(true);
            }else{
                tbEstadoTarea.setChecked(false);
            }
        }

        public void actualizarEstado(int id_tarea, int estado_boton) {
            String url = Util.RUTA_ACTUALIZAR_TAREA;
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    dialogRegistroExitoso(titulo,descripcion,fechaSeleccionada,response);
                    if(estado_boton == 1){
                        Toast.makeText(context, "Tarea Activada", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Tarea Desactivada ", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "error " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("estado_id", String.valueOf(estado_boton));
                    params.put("id_tarea", String.valueOf(id_tarea));
                    return params;
                }
            };
            requestQueue.add(request);
        }
    }
}
