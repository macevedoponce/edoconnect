package com.acevedo.educonnect.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.acevedo.educonnect.Login.LoginActivity;
import com.acevedo.educonnect.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    TextView txtDerechos, txtOne, txtYear;
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);

        //animacion
        Animation animacion1 = AnimationUtils.loadAnimation(this, com.acevedo.educonnect.commonresources.R.anim.des_top);
        Animation animacion2 = AnimationUtils.loadAnimation(this, com.acevedo.educonnect.commonresources.R.anim.des_bot);

        txtDerechos = findViewById(R.id.txtDerechos);
        txtYear = findViewById(R.id.txtYear);
        txtOne = findViewById(R.id.txtOne);
        imgLogo = findViewById(R.id.imgLogo);

        txtYear.setText(year+"");


        txtDerechos.setAnimation(animacion1);
        txtYear.setAnimation(animacion2);
        txtOne.setAnimation(animacion2);
        imgLogo.setAnimation(animacion2);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("tipo_usuario",1);
                startActivity(i);
                finish();
            }
        },3600);
    }
}