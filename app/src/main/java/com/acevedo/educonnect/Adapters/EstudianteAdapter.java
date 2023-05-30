package com.acevedo.educonnect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acevedo.educonnect.Clases.Estudiante;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.ui.docente.curso.asistencia.RegistrarAsistenciaActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EstudianteAdapter extends RecyclerView.Adapter<EstudianteAdapter.EstudianteHolder> implements View.OnClickListener {

    Context context;
    List<Estudiante> estudianteList;
    View.OnClickListener listener;

    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String fechaActual = dateFormat.format(date);

    public EstudianteAdapter(Context context, List<Estudiante> estudianteList) {
        this.context = context;
        this.estudianteList = estudianteList;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public EstudianteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.estudiante_item, parent,false);
        mView.setOnClickListener(this);
        return new EstudianteHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull EstudianteAdapter.EstudianteHolder holder, int position) {
        Estudiante estudiante = estudianteList.get(position);
        String dni = estudiante.getDni();
        String nombres = estudiante.getNombres();
        String apellidos = estudiante.getApellidos();
        String estado = estudiante.getEstado();
        int id_participante = estudiante.getId();

        holder.setDni(dni);
        holder.setNombre(nombres);
        holder.setApellido(apellidos);
        holder.setEstado(estado);

        //holder.setParticipantesGuardados(id_participante);
        holder.rgAsistencia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // Mostrar la posición del RecyclerView
                String estado;
                if (i == R.id.rbTemprano) {
                    estado = "Temprano";
                } else if (i == R.id.rbTarde) {
                    estado = "Tarde";
                } else {
                    estado = "Falta";
                }

                //ejecutar funcion registrarAsistencia que esta en ListParticipantesActivity
                ((RegistrarAsistenciaActivity) radioGroup.getContext()).registrarAsistencia(id_participante,estado);

            }
        });
    }

    @Override
    public int getItemCount() {
        return estudianteList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }

    public class EstudianteHolder extends RecyclerView.ViewHolder {

        TextView tvDni, tvNombre, tvApellido;
        RadioGroup rgAsistencia;
        RadioButton rbTemprano, rbTarde, rbFalta;
        View view;

        public EstudianteHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvDni = view.findViewById(R.id.tvDniEstudiante);
            tvNombre = view.findViewById(R.id.tvNombreEstudiante);
            tvApellido = view.findViewById(R.id.tvApellidoEstudiante);
            rgAsistencia = view.findViewById(R.id.rgAsistencia);
            rbTemprano = view.findViewById(R.id.rbTemprano);
            rbTarde = view.findViewById(R.id.rbTarde);
            rbFalta = view.findViewById(R.id.rbFalta);
        }

        public void setDni(String dni) {
            tvDni.setText(dni);
        }

        public void setNombre(String nombre) {
            tvNombre.setText(nombre);
        }

        public void setApellido(String apellido) {
            tvApellido.setText(apellido);
        }

        public void setEstado(String estado) {
            int radioButtonIndex;

            switch (estado){
                case "Temprano":
                    radioButtonIndex = R.id.rbTemprano;
                    break;
                case "Tarde":
                    radioButtonIndex = R.id.rbTarde;
                    break;
                case "Falta":
                    radioButtonIndex = R.id.rbFalta;
                    break;
                default:
                    radioButtonIndex = -1;
                    break;
            }

            // Seleccionar el radiobutton correspondiente al estado
            if (radioButtonIndex != -1) {
                RadioButton radioButton = itemView.findViewById(radioButtonIndex);
                radioButton.setChecked(true);
            }

        }
    }
}
