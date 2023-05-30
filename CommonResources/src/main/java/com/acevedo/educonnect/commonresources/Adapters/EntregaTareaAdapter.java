package com.acevedo.educonnect.commonresources.Adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.acevedo.educonnect.commonresources.Clases.EntregaTareas;

import com.acevedo.educonnect.commonresources.R;
import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;


import java.util.List;

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
        View mView = LayoutInflater.from(context).inflate(com.acevedo.educonnect.commonresources.R.layout.entrega_tarea_item, parent,false);
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
            ivEntregaTarea = view.findViewById(com.acevedo.educonnect.commonresources.R.id.ivEntregaTarea);
            cvEntregaTarea = view.findViewById(com.acevedo.educonnect.commonresources.R.id.cvEntregaTarea);

        }

        public void setImagen(String url_trabajo) {

           if(url_trabajo.endsWith(".pdf")){
               Glide.with(context).load("https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2021/12/22/16401922123443.jpg").into(ivEntregaTarea);
           }else{
               Glide.with(context).load(url_trabajo).into(ivEntregaTarea);
           }

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
                cvEntregaTarea.setStrokeColor(ContextCompat.getColor(context, com.acevedo.educonnect.commonresources.R.color.success));
            }
        }
    }
}
