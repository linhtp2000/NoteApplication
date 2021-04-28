package com.example.appnote.ui.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnote.ui.Database.Entity.Category;
import com.example.appnote.ui.Database.Entity.Note;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Query("SELECT * FROM Category WHERE Name = :name AND Date = :date AND UserId = :userid")
    Category getCategory(String name, String date,int userid);

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);
    @Delete
    void delete(Category category);


    @Delete
    void delete(Category... categories);
}
