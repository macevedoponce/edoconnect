package com.acevedo.educonnect.commonresources.Adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.commonresources.Clases.Tarea;
import com.acevedo.educonnect.commonresources.Util.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TareaEstudianteAdapter extends RecyclerView.Adapter<TareaEstudianteAdapter.TareaEstudianteHolder> implements View.OnClickListener {

    Context context;
    List<Tarea> tareaList;
    View.OnClickListener listener;


    public TareaEstudianteAdapter(Context context, List<Tarea> tareaList) {
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
    public TareaEstudianteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(com.acevedo.educonnect.commonresources.R.layout.tarea_item, parent,false);
        mView.setOnClickListener(this);
        return new TareaEstudianteHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaEstudianteAdapter.TareaEstudianteHolder holder, int position) {
        Tarea tarea = tareaList.get(position);
        String titulo = tarea.getTitulo();
        String retroalimentacion = tarea.getRetroalimentacion();
        String url_trabajo = tarea.getUrl_trabajo();
        int nota = tarea.getNota();

        holder.setTitulo(titulo);
        if(url_trabajo.length() > 60){
            holder.tbEstadoTarea.setChecked(true);
        }

        if(retroalimentacion.length() >= 5 && nota > 0){
            float strokeWidthInSp = 4f;

            float strokeWidthInPixels = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, strokeWidthInSp, context.getResources().getDisplayMetrics()
            );

            holder.cvTarea.setStrokeWidth((int) strokeWidthInPixels);
            holder.cvTarea.setStrokeColor(ContextCompat.getColor(context, com.acevedo.educonnect.commonresources.R.color.success));
        }


    }

    @Override
    public int getItemCount() {
        return tareaList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }

    public class TareaEstudianteHolder extends RecyclerView.ViewHolder {

        TextView tvTituloTarea;
        MaterialCardView cvTarea;
        ToggleButton tbEstadoTarea;
        View view;

        RequestQueue requestQueue;
        public TareaEstudianteHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            cvTarea = view.findViewById(com.acevedo.educonnect.commonresources.R.id.cvTarea);
            tvTituloTarea = view.findViewById(com.acevedo.educonnect.commonresources.R.id.tvTituloTarea);
            tbEstadoTarea = view.findViewById(com.acevedo.educonnect.commonresources.R.id.tbEstadoTarea);
            requestQueue = Volley.newRequestQueue(context);
            tbEstadoTarea.setClickable(false);
        }

        public void setTitulo(String titulo) {
            tvTituloTarea.setText(titulo);
        }

    }
}
