package com.example.appnote.ui.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnote.ui.Database.Entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category WHERE UserId = :id")
    public List<Category> getAll(int id);

    @Query("SELECT * FROM Category WHERE Name =:name AND UserId = :id")
    public Category getCategory(String name, int id);


    @Query("SELECT Name FROM Category WHERE UserId = :id")
    public String[] getCateName(int id);

    @Query("SELECT Name FROM Category WHERE Id = :id AND UserId = :uid")
    public String getNameFromId(int id, int uid);

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);
    @Delete
    void delete(Category category);

    @Delete
    void delete(Category... categories);

    @Query("Select Name from Category where UserId =:i")
    public String[] getNameCate(int i);
}
