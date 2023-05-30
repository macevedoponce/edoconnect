package com.acevedo.educonnect.commonresources.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.commonresources.Clases.Curso;

import com.bumptech.glide.Glide;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoHolder> implements View.OnClickListener {

    Context context;
    List<Curso> cursoList;
    View.OnClickListener listener;


    public CursoAdapter(Context context, List<Curso> cursoList) {
        this.context = context;
        this.cursoList = cursoList;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public CursoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(com.acevedo.educonnect.commonresources.R.layout.curso_item, parent,false);
        mView.setOnClickListener(this);
        return new CursoHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoAdapter.CursoHolder holder, int position) {
        Curso curso = cursoList.get(position);
        String nombre = curso.getCursoNombre();
        String url_img = curso.getImg_url();
        String grado = curso.getGrado();
        String seccion = curso.getSeccion();

        holder.setNombre(nombre);
        holder.setImagen(url_img);
        holder.setGradoSeccion(grado, seccion);
    }

    @Override
    public int getItemCount() {
        return cursoList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }

    public class CursoHolder extends RecyclerView.ViewHolder {

        TextView tvGradoSeccion, tvNombreCurso;
        ImageView ivCurso;
        View view;
        public CursoHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvGradoSeccion = view.findViewById(com.acevedo.educonnect.commonresources.R.id.tvGradoSeccion);
            tvNombreCurso = view.findViewById(com.acevedo.educonnect.commonresources.R.id.tvNombreCurso);
            ivCurso = view.findViewById(com.acevedo.educonnect.commonresources.R.id.ivCurso);

        }

        public void setNombre(String nombre) {
            tvNombreCurso.setText(nombre);
        }

        public void setImagen(String url_img) {
            Glide.with(context).load(url_img).into(ivCurso);
        }

        public void setGradoSeccion(String grado, String seccion) {
            tvGradoSeccion.setText(grado + seccion);
        }
    }
}
