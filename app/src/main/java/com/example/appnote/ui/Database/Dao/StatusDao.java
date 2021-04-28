package com.example.appnote.ui.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnote.ui.Database.Entity.Status;

import java.util.List;

@Dao
public interface StatusDao {
    @Query("SELECT * FROM Status")
    List<Status> getAll();

    @Insert
    void insert(Status status);

    @Update
    void update(Status status);
    @Delete
    void delete(Status status);


    @Delete
    void delete(Status... statuses);
}
