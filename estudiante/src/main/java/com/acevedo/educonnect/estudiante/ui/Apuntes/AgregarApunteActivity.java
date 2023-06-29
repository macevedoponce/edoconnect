package com.acevedo.educonnect.estudiante.ui.Apuntes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.acevedo.educonnect.estudiante.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarApunteActivity extends AppCompatActivity {

    TextInputLayout tilTitle, tilNote;
    TextInputEditText tietTitle, tietNote;

    CardView cvSaveNote;

    Note notes;

    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_apunte);
        tilTitle = findViewById(R.id.tilTitle);
        tilNote = findViewById(R.id.tilNote);
        tietTitle = findViewById(R.id.tietTitle);
        tietNote = findViewById(R.id.tietNote);
        cvSaveNote = findViewById(R.id.cvSaveNote);

        notes = new Note();
        try{
            notes = (Note) getIntent().getSerializableExtra("old_note");
            tietTitle.setText(notes.getTitle());
            tietNote.setText(notes.getNotes());
            isOldNote = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        cvSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = tietTitle.getText().toString();
                String description = tietNote.getText().toString();

                if(!description.isEmpty() && !title.isEmpty()){
                    SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                    Date date = new Date();

                    if(!isOldNote){
                        notes = new Note();
                    }
                    notes.setTitle(title);
                    notes.setNotes(description);
                    notes.setDate(formatter.format(date));

                    Intent intent = new Intent();
                    intent.putExtra("note", notes);
                    setResult(Activity.RESULT_OK, intent);
                    finish();

                }else{
                    if(description.isEmpty()){
                        tilTitle.setError("Ingrese Descripción");
                    }
                    if(title.isEmpty()){
                        tilNote.setError("Ingrese Descripción");
                    }
                }

            }
        });
    }
}