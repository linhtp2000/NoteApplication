package com.example.appnote.ui.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnote.ui.Database.Entity.Priority;

import java.util.List;

@Dao
public interface PriorityDao {
    @Query("SELECT * FROM Priority")
    List<Priority> getAll();

    @Insert
    void insert(Priority priority);

    @Update
    void update(Priority priority);
    @Delete
    void delete(Priority priority);


    @Delete
    void delete(Priority ... priorities);
}
