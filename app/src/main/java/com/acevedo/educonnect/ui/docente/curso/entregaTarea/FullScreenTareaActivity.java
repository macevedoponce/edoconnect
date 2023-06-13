package com.acevedo.educonnect.ui.docente.curso.entregaTarea;

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
import com.acevedo.educonnect.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;



public class FullScreenTareaActivity extends AppCompatActivity {

    CardView cvFullScreen;
    WebView webView;

    SubsamplingScaleImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_tarea);
        cvFullScreen = findViewById(R.id.cvFullScreen);
        imageView = findViewById(R.id.imageView);
        webView = findViewById(R.id.webView);

        String url_trabajo = getIntent().getStringExtra("url_trabajo");

        if(url_trabajo.endsWith(".pdf")){
            imageView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url_trabajo);

        }else{
            imageView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            // Aumentar el zoom
            imageView.setScaleAndCenter(imageView.getMaxScale(), imageView.getCenter());

            // Establecer límites de zoom mínimo y máximo
            imageView.setMaxScale(10f); // Zoom máximo permitido
            imageView.setMinScale(1f);  // Zoom mínimo permitido

            Glide.with(this)
                    .load(url_trabajo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                            imageView.setImage(ImageSource.bitmap(bitmap));
                        }
                    });

        }

        cvFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}