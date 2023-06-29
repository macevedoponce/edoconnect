package com.acevedo.educonnect.estudiante.ui.Apuntes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface MainDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAll();

    @Query("UPDATE notes SET title = :title, notes= :notes WHERE ID = :id")
    void update(int id, String title , String notes);

    @Delete
    void delete(Note notes);

    @Query("UPDATE notes SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);

}
