package com.acevedo.educonnect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.Clases.Examen;
import com.acevedo.educonnect.Clases.Preguntas;
import com.acevedo.educonnect.R;

import java.util.List;

public class PreguntaExamenAdapter extends RecyclerView.Adapter<PreguntaExamenAdapter.PreguntaExamenHolder> implements View.OnClickListener{

    Context context;
    List<Examen> examenList;
    @Override
    public void onClick(View v) {

    }
    public PreguntaExamenAdapter(Context context, List<Examen> examenList) {
        this.context = context;
        this.examenList = examenList;
    }
    @NonNull
    @Override
    public PreguntaExamenAdapter.PreguntaExamenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.pregunta_examen_item, parent,false);
        mView.setOnClickListener(this);
        return new PreguntaExamenAdapter.PreguntaExamenHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreguntaExamenAdapter.PreguntaExamenHolder holder, int position) {
        Integer idExamen,estado;
        String nombreExamen, exmDescripcion;
        idExamen= Integer.valueOf(examenList.get(position).getIdExamen().toString());
        estado= Integer.valueOf(examenList.get(position).getEstado().toString());
        exmDescripcion = examenList.get(position).getDescripcion();

        holder.txtTituloExamen.setText(examenList.get(position).getNombreExamen());
    }

    @Override
    public int getItemCount() {
        return examenList.size();
    }

    public class PreguntaExamenHolder extends RecyclerView.ViewHolder {
        TextView txtTituloExamen;
        public PreguntaExamenHolder(@NonNull View itemView) {
            super(itemView);
            txtTituloExamen = itemView.findViewById(R.id.tvTituloPreguntaExamen);
        }
    }
}
