package com.example.appnote.ui.Database.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "Id",
        childColumns = "UserId",
        onDelete = CASCADE,onUpdate = SET_NULL))
public class Status {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Name;
    private String Date;

    public Status(){}
    public Status(int id, String name, String date, int idUser) {
        Id = id;
        Name = name;
        Date = date;
        UserId = idUser;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
    public int  UserId;


}
