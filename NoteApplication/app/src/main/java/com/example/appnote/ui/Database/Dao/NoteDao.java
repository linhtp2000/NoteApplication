package com.example.appnote.ui.Database.Dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnote.ui.Database.Entity.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note WHERE UserId = :id")
    public List<Note> getAll(int id);
    @Query("SELECT StatId FROM Note WHERE UserId = :id")
    public int[] getStatusId(int id);
    @Insert
    void insert(Note note);

    @Update
    void update(Note repos);
    @Delete
    void delete(Note note);

    @Delete
    void delete(Note... note);

}
