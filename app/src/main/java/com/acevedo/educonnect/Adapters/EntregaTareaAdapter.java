package com.acevedo.educonnect.Adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.Clases.EntregaTareas;
import com.acevedo.educonnect.Clases.Tarea;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.Util.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntregaTareaAdapter extends RecyclerView.Adapter<EntregaTareaAdapter.EntregaTareaHolder> implements View.OnClickListener {

    Context context;
    List<EntregaTareas> entregaTareaList;
    View.OnClickListener listener;


    public EntregaTareaAdapter(Context context, List<EntregaTareas> entregaTareaList) {
        this.context = context;
        this.entregaTareaList = entregaTareaList;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public EntregaTareaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.entrega_tarea_item, parent,false);
        mView.setOnClickListener(this);
        return new EntregaTareaHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntregaTareaAdapter.EntregaTareaHolder holder, int position) {
        EntregaTareas entregaTareas = entregaTareaList.get(position);
        String nombres = entregaTareas.getUs_nombres();
        String apellidos = entregaTareas.getUs_apellidos();
        String retroalimentacion = entregaTareas.getRetroalimentacion();
        String url_trabajo = entregaTareas.getUrl_trabajo();

        holder.setImagen(url_trabajo);
        holder.setNombreEstudiante(nombres, apellidos);
        holder.setEstadoEntrega(retroalimentacion);

    }

    @Override
    public int getItemCount() {
        return entregaTareaList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }

    public class EntregaTareaHolder extends RecyclerView.ViewHolder {

        TextView tvEstudiante;
        ImageView ivEntregaTarea;
        MaterialCardView cvEntregaTarea;

        View view;

        RequestQueue requestQueue;
        public EntregaTareaHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvEstudiante = view.findViewById(R.id.tvEstudiante);
            ivEntregaTarea = view.findViewById(R.id.ivEntregaTarea);
            cvEntregaTarea = view.findViewById(R.id.cvEntregaTarea);
        }

        public void setImagen(String url_trabajo) {
            Glide.with(context).load(url_trabajo).into(ivEntregaTarea);
        }

        public void setNombreEstudiante(String nombres, String apellidos) {
            tvEstudiante.setText(nombres +" "+apellidos);
        }

        public void setEstadoEntrega(String retroalimentacion) {
            if(retroalimentacion.length() >= 10){

                float strokeWidthInSp = 4f; // Valor deseado en sp

                float strokeWidthInPixels = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, strokeWidthInSp, context.getResources().getDisplayMetrics()
                );

                cvEntregaTarea.setStrokeWidth((int) strokeWidthInPixels);
                cvEntregaTarea.setStrokeColor(ContextCompat.getColor(context, R.color.success));
            }
        }
    }
}
