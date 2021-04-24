package com.example.appnote.ui.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    List<Note> getAll();

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);
    @Delete
    void delete(Category category);


    @Delete
    void delete(Category... categories);
}
