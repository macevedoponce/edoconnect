package com.acevedo.educonnect.estudiante.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.acevedo.educonnect.estudiante.R;
import com.acevedo.educonnect.estudiante.ui.Apuntes.AgregarApunteActivity;
import com.acevedo.educonnect.estudiante.ui.Apuntes.Note;
import com.acevedo.educonnect.estudiante.ui.Apuntes.NotesAdapter;
import com.acevedo.educonnect.estudiante.ui.Apuntes.NotesClickListener;
import com.acevedo.educonnect.estudiante.ui.Apuntes.RoomDB;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ApuntesFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{

    RecyclerView rvNotes;
    NotesAdapter notesAdapter;
    List<Note> notes = new ArrayList<>();
    RoomDB database;
    CardView cvAddNote;
    Note selectedNote;

    Note notesAdd = new Note();
    boolean isOldNote = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_apuntes, container, false);
        rvNotes = vista.findViewById(R.id.rvNotes);
        cvAddNote = vista.findViewById(R.id.cvAddNote);

        database = RoomDB.getInstance(getContext());
        notes = database.mainDAO().getAll();

        updateRecycler(notes);

        cvAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(getContext(), AgregarApunteActivity.class);
                //startActivityForResult(i,101);

                addApunte(null);
            }
        });
        return vista;
    }

    private void addApunte(Note noteRecep) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(com.acevedo.educonnect.commonresources.R.layout.dialog_agregar_apunte);

        TextInputLayout tilTitle = dialog.findViewById(com.acevedo.educonnect.commonresources.R.id.tilTitleDialog);
        TextInputLayout tilNote = dialog.findViewById(com.acevedo.educonnect.commonresources.R.id.tilNoteDialog);
        TextInputEditText tietTitle = dialog.findViewById(com.acevedo.educonnect.commonresources.R.id.tietTitleDialog);
        TextInputEditText tietNote = dialog.findViewById(com.acevedo.educonnect.commonresources.R.id.tietNoteDialog);
        CardView cvGuardarApunte = dialog.findViewById(com.acevedo.educonnect.commonresources.R.id.cvGuardarApunte);

        ImageView ivCloseDialogDetalleTarea = dialog.findViewById(com.acevedo.educonnect.commonresources.R.id.ivCloseDialogDetalleTarea);


        if(noteRecep != null){
            tietTitle.setText(noteRecep.getTitle());
            tietNote.setText(noteRecep.getNotes());
            isOldNote = true;
        }
        else{
            isOldNote = false;
        }

        cvGuardarApunte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = tietTitle.getText().toString();
                String description = tietNote.getText().toString();


                if(!description.isEmpty() && !title.isEmpty()){
                    SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                    Date date = new Date();

                    if(!isOldNote){ //se agrega nuevo apunte
                        notesAdd = new Note();
                        notesAdd.setTitle(title);
                        notesAdd.setNotes(description);
                        notesAdd.setDate(formatter.format(date));
                        database.mainDAO().insert(notesAdd);
                        notes.clear();
                        notes.addAll(database.mainDAO().getAll());
                        notesAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }else{
                        notesAdd.setTitle(title);
                        notesAdd.setNotes(description);
                        notesAdd.setDate(formatter.format(date));
                        database.mainDAO().update(noteRecep.getID(), notesAdd.getTitle(), notesAdd.getNotes());
                        notes.clear();
                        notes.addAll(database.mainDAO().getAll());
                        notesAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                }else{
                    if(description.isEmpty()){
                        tilNote.setError("Ingrese Descripción");
                    }
                    if(title.isEmpty()){
                        tilTitle.setError("Ingrese Titulo");
                    }
                }

            }
        });

        ivCloseDialogDetalleTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = com.acevedo.educonnect.commonresources.R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==101){
//            if(resultCode == Activity.RESULT_OK){
//                Note newNote = (Note) data.getSerializableExtra("note");
//                database.mainDAO().insert(newNote);
//                notes.clear();
//                notes.addAll(database.mainDAO().getAll());
//                notesAdapter.notifyDataSetChanged();
//            }
//        }
//        else if(requestCode==102){
//            if(resultCode == Activity.RESULT_OK){
//                Note newNote = (Note) data.getSerializableExtra("note");
//                database.mainDAO().update(newNote.getID(), newNote.getTitle(), newNote.getNotes());
//                notes.clear();
//                notes.addAll(database.mainDAO().getAll());
//                notesAdapter.notifyDataSetChanged();
//            }
//        }
//    }

    private void updateRecycler(List<Note> notes) {
        rvNotes.setHasFixedSize(true);
        rvNotes.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesAdapter = new NotesAdapter(getContext(),notes,notesClickListener);
        rvNotes.setAdapter(notesAdapter);
    }
    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Note notes) {
            addApunte(notes);
//            Intent intent = new Intent(getContext(), AgregarApunteActivity.class);
//            intent.putExtra("old_note", notes);
//            startActivityForResult(intent,102);
        }

        @Override
        public void OnLongClick(Note notes, CardView cardView) {
            selectedNote = new Note();
            selectedNote = notes;
            showPopUp(cardView);
        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(getContext(), cardView);
        popupMenu.setOnMenuItemClickListener(ApuntesFragment.this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.pin:
                if(selectedNote.isPinned()){
                    database.mainDAO().pin(selectedNote.getID(), false);
                    Toast.makeText(getContext(), "Desmarcado", Toast.LENGTH_SHORT).show();
                }else{
                    database.mainDAO().pin(selectedNote.getID(), true);
                    Toast.makeText(getContext(), "Marcado", Toast.LENGTH_SHORT).show();
                }
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.mainDAO().delete(selectedNote);
                notes.remove(selectedNote);
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Apunte Eliminado", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;
        }
    }
}