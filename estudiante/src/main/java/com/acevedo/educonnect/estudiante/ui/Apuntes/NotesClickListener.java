package com.acevedo.educonnect.estudiante.ui.Apuntes;

import androidx.cardview.widget.CardView;

public interface NotesClickListener {
    void onClick(Note notes);
    void OnLongClick(Note notex, CardView cardView);
}
