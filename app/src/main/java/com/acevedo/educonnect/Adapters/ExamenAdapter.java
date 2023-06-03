package com.acevedo.educonnect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.Clases.Examen;
import com.acevedo.educonnect.R;

import java.util.List;

public class ExamenAdapter extends RecyclerView.Adapter<ExamenAdapter.ExamenHolder> implements View.OnClickListener {

    Context context;
    List<Examen> examenList;
    @Override
    public void onClick(View v) {

    }

    public ExamenAdapter(Context context, List<Examen> examenList) {
        this.context = context;
        this.examenList = examenList;
    }

    @NonNull
    @Override
    public ExamenAdapter.ExamenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.examen_item, parent,false);
        mView.setOnClickListener(this);
        return new ExamenAdapter.ExamenHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamenAdapter.ExamenHolder holder, int position) {
        holder.tvTituloExamen.setText(examenList.get(position).getNombreExamen());

    }

    @Override
    public int getItemCount() {
        return examenList.size();
    }

    public class ExamenHolder extends RecyclerView.ViewHolder {
        TextView tvTituloExamen;
        public ExamenHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloExamen = itemView.findViewById(R.id.tvTituloExamen);
        }
    }
}
