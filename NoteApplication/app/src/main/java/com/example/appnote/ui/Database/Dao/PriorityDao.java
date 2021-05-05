package com.example.appnote.ui.Database.Dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnote.ui.Database.Entity.Category;
import com.example.appnote.ui.Database.Entity.Priority;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PriorityDao {
    @Query("SELECT * FROM Priority where UserId = :id")
    List<Priority> getAll(int id);

    @Query("SELECT * FROM Priority WHERE Name =:name AND UserId = :id")
    public Priority getPriority(String name, int id);

    @Query("SELECT Name FROM Priority WHERE Id = :id AND UserId = :uid")
    public String getNameFromId(int id, int uid);

    @Query("SELECT Name FROM Priority WHERE UserId = :id")
    public String[] getPrioName(int id);

    @Query("SELECT * FROM Priority where UserId = :i")
    public Cursor getPriority(int i);
    @Insert
    void insert(Priority priority);

    @Update
    void update(Priority priority);
    @Delete
    void delete(Priority priority);



    @Delete
    void delete(Priority ... priorities);

    @Query("Select Name from Priority where UserId =:i")
    public String[] getNamePrio(int i);
}
