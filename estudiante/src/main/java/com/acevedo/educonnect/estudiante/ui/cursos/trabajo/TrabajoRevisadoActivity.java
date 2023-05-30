package com.acevedo.educonnect.estudiante.ui.cursos.trabajo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acevedo.educonnect.estudiante.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class TrabajoRevisadoActivity extends AppCompatActivity {

    LinearLayout llRegresar;
    TextView tvNota,tvTituloTarea,tvRetroalimentacion;
    SubsamplingScaleImageView ivContenido;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajo_revisado);

        llRegresar = findViewById(R.id.llRegresar);
        tvNota = findViewById(R.id.tvNota);
        tvRetroalimentacion = findViewById(R.id.tvRetroalimentacion);
        ivContenido = findViewById(R.id.ivContenidoEstudiante);
        webView = findViewById(R.id.webViewEstudiante);
        tvTituloTarea = findViewById(R.id.tvTituloTarea);

        llRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        //obtener datos de intent
        int nota_recep = getIntent().getIntExtra("nota", 0);
        String titulo_tarea_recep = getIntent().getStringExtra("titulo_tarea");
        String retroalimentacion_recep = getIntent().getStringExtra("retroalimentacion");
        String url_trabajo_recep = getIntent().getStringExtra("url_trabajo");

        tvNota.setText(nota_recep+"");
        tvRetroalimentacion.setText(retroalimentacion_recep);
        tvTituloTarea.setText(titulo_tarea_recep);

        if(url_trabajo_recep.endsWith(".pdf")){
            ivContenido.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url_trabajo_recep);

        }else{
            ivContenido.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            // Aumentar el zoom
            ivContenido.setScaleAndCenter(ivContenido.getMaxScale(), ivContenido.getCenter());

            // Establecer límites de zoom mínimo y máximo
            ivContenido.setMaxScale(10f); // Zoom máximo permitido
            ivContenido.setMinScale(1f);  // Zoom mínimo permitido

            Glide.with(this)
                    .load(url_trabajo_recep)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                            ivContenido.setImage(ImageSource.bitmap(bitmap));
                        }
                    });

        }
    }
}