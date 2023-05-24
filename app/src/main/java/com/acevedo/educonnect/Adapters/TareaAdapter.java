package com.acevedo.educonnect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.Clases.Curso;
import com.acevedo.educonnect.Clases.Tarea;
import com.acevedo.educonnect.R;
import com.bumptech.glide.Glide;

import java.util.List;

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
        int estado = tarea.getEstado();

        holder.setTitulo(titulo);
        holder.setEstado(estado);
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
        public TareaHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvTituloTarea = view.findViewById(R.id.tvTituloTarea);
            //rbEstadoTarea = view.findViewById(R.id.rbEstadoTarea);
            tbEstadoTarea = view.findViewById(R.id.tbEstadoTarea);

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
    }
}
