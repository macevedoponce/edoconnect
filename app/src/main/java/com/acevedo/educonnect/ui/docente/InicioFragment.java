package com.acevedo.educonnect.ui.docente;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.acevedo.educonnect.R;


public class InicioFragment extends Fragment {

    TextView tvBienvenida;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        tvBienvenida = vista.findViewById(R.id.tvBienvenida);
        cargarPreferencias();
        return vista;
    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getContext().getSharedPreferences("usuarioLoginDocente", Context.MODE_PRIVATE);
        String nombre = preferences.getString("nombres","");
        String apellidos = preferences.getString("apellidos","");
        String dni = preferences.getString("dni","");
        int rol = preferences.getInt("rol",0);
        String bienvenidida ="Bienvenido " + nombre + " " + apellidos + " " + dni;
        tvBienvenida.setText(bienvenidida);
        //Toast.makeText(getContext(), "Bienvenido: " + nombre + " " + apellidos, Toast.LENGTH_SHORT).show();

    }
}