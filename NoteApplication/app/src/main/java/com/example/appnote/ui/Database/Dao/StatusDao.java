package com.example.appnote.ui.Database.Dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnote.ui.Database.Entity.Category;
import com.example.appnote.ui.Database.Entity.Status;

import java.util.List;

@Dao
public interface StatusDao {
    @Query("SELECT * FROM Status WHERE UserId = :id")
    public List<Status> getAll(int id);

    @Query("SELECT * FROM Status WHERE Name =:name AND UserId = :id")
    public Status getStatus(String name, int id);


    @Query("SELECT Name FROM Status WHERE Id = :id AND UserId = :uid")
    public String getNameFromId(int id, int uid);

    @Query("SELECT Name FROM Status WHERE UserId = :id")
    public String[] getStaName(int id);
    @Insert
    public void insert(Status status);

    @Update
    public void update(Status status);
    @Delete
    public void delete(Status status);

    @Query("Update Status set Name = :n where Name = :e and Date = :c and UserId = :d")
    public  void updateData(String n, String e, String c, int d);
    @Query("delete from Status where Name = :e and Date = :c and UserId = :r")
    public  void deleteData(String e, String c, int r);
    @Delete
    public void delete(Status... statuses);
    @Query("Select Name from Status where UserId =:i")
    public String[] getNameSta(int i);
}
