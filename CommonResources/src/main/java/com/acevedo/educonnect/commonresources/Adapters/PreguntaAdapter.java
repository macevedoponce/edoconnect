package com.acevedo.educonnect.commonresources.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.commonresources.Clases.Alternativas;
import com.acevedo.educonnect.commonresources.Clases.Preguntas;
import com.acevedo.educonnect.commonresources.R;

import java.util.List;

public class PreguntaAdapter extends RecyclerView.Adapter<PreguntaAdapter.PreguntaHolder> implements View.OnClickListener {
    Context context;
    List<Preguntas> preguntasList;
    View.OnClickListener listener;
    @Override
    public void onClick(View v) {

    }

    public PreguntaAdapter(Context context, List<Preguntas> preguntasList) {
        this.context = context;
        this.preguntasList = preguntasList;
    }

    @NonNull
    @Override
    public PreguntaAdapter.PreguntaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.pregunta_item, parent,false);
        mView.setOnClickListener(this);
        return new PreguntaAdapter.PreguntaHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreguntaAdapter.PreguntaHolder holder, int position) {

        Integer idpregunta;
        String retroalimentacion;
        idpregunta = preguntasList.get(position).getId_pregunta();
        retroalimentacion = preguntasList.get(position).getId_pregunta().toString();
        holder.txtTituloPregunta.setText(preguntasList.get(position).getNombre());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtener la pregunta seleccionada y sus alternativas
                Preguntas preguntaSeleccionada = preguntasList.get(holder.getAdapterPosition());
                List<Alternativas> alternativasPregunta = preguntaSeleccionada.getAlternativas();

                // Crear el AlertDialog y establecer su contenido
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_detalle_pregunta, null);
                builder.setView(dialogView);

                // Obtener las vistas del layout del dialog
                TextView tvTituloDetallePregunta = dialogView.findViewById(R.id.tvTituloDetallePregunta);
                TextView tvdialogPreguntaCorrecta = dialogView.findViewById(R.id.tvdialogPreguntaCorrecta);
                TextView tvDialogIncorrecta1 = dialogView.findViewById(R.id.tvDialogIncorrecta1);
                TextView tvDialogIncorrecta2 = dialogView.findViewById(R.id.tvDialogIncorrecta2);
                TextView tvDialogIncorrecta3 = dialogView.findViewById(R.id.tvDialogIncorrecta3);
                ImageView ivCloseDialogDetallePregunta = dialogView.findViewById(R.id.ivCloseDialogDetallePregunta);

                // Establecer los textos de la pregunta y las alternativas
                tvTituloDetallePregunta.setText(preguntaSeleccionada.getNombre());
                tvdialogPreguntaCorrecta.setText(alternativasPregunta.get(0).getNombreAlternativa());
                tvDialogIncorrecta1.setText(alternativasPregunta.get(1).getNombreAlternativa());
                tvDialogIncorrecta2.setText(alternativasPregunta.get(2).getNombreAlternativa());
                tvDialogIncorrecta3.setText(alternativasPregunta.get(3).getNombreAlternativa());

                // Mostrar el AlertDialog
                AlertDialog dialog = builder.create();
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                // Configurar el botón de cerrar
                ivCloseDialogDetallePregunta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



            }
        });
    }

    @Override
    public int getItemCount() {
        return preguntasList.size();
    }


    public class PreguntaHolder extends RecyclerView.ViewHolder {

        TextView txtTituloPregunta;
        CardView cvTituloPregunta;
        public PreguntaHolder(@NonNull View itemView) {
            super(itemView);
            txtTituloPregunta = itemView.findViewById(R.id.txtTituloPregunta);
            cvTituloPregunta = itemView.findViewById(R.id.cvPreguntas);
        }
    }
}
