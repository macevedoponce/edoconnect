package com.acevedo.educonnect.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acevedo.educonnect.MainActivity;
import com.acevedo.educonnect.R;
import com.acevedo.educonnect.Util.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout tilCorreo, tilPassword;
    EditText edtCorreo, edtPassword;
    Button btnEntrar;
    TextView txtLogin;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        tilCorreo =findViewById(R.id.tilCorreo);
        tilPassword =findViewById(R.id.tilPassword);
        edtCorreo =findViewById(R.id.edtCorreo);
        edtPassword =findViewById(R.id.edtPassword);
        btnEntrar =findViewById(R.id.btnEntrar);
        txtLogin =findViewById(R.id.txtLogin);

        recuperarPreferencias();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario();
            }
        });
    }

    private void validarUsuario() {
        String dni = edtCorreo.getText().toString();
        String password = edtPassword.getText().toString();

        if(!dni.isEmpty() && !password.isEmpty()) {
            String url = Util.RUTA_LOGIN + "?" +
                    "usuario=" + dni +
                    "&contrasena=" + password;

            url = url.replace(" ", "%20");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    //guardar datos de usuario


                    JSONArray json = response.optJSONArray("usuario");
                    JSONObject jsonObject = null;

                    SharedPreferences preferences = getSharedPreferences("usuarioLogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    try {
                        jsonObject = json.getJSONObject(0);

                        editor.putString("dni", dni);
                        editor.putString("password", password);
                        editor.putInt("id", jsonObject.getInt("id"));
                        editor.putString("nombres", jsonObject.getString("nombres"));
                        editor.putString("apellidos", jsonObject.getString("apellidos"));
                        editor.putInt("rol", jsonObject.getInt("rol"));
                        editor.putBoolean("session", true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.commit();


                    //fin guardar datos de usuario

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    edtCorreo.setText("");
                    edtPassword.setText("");
                    txtLogin.setText("Usuario no encontrado, intente de nuevo!");
                    txtLogin.setTextColor(Color.RED);
                }
            });
            requestQueue.add(jsonObjectRequest);
        }else{
            tilCorreo.setError("Ingrese usuario (DNI)");
            tilPassword.setError("Ingrese Contraseña");
        }
    }

    private void recuperarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("usuarioLogin", Context.MODE_PRIVATE);
        String username = preferences.getString("dni","");
        String password = preferences.getString("password","");
        edtCorreo.setText(username);
        edtPassword.setText(password);

        if(!username.equals("") && !password.equals("")){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }
}