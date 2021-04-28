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
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Name;
    private String Date;
   public int  UserId;

    public Category(){}
  public Category(int id, String name, String date, int idUser) {
        Id = id;
        Name = name;
        Date = date;
        UserId = idUser;
    }
    public Category(String name, String date, int idUser) {

        Name = name;
        Date = date;
        UserId = idUser;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public String getDate() {
        return Date;
    }


}
