package com.example.appnote.ui.Database;

import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

public class Status {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Name;
    private String Date;
    public int  UserId;
    @ForeignKey(
            entity = User.class,
            parentColumns ="Id",
            childColumns = "UserId"
    )
    public Status(int id, String name, String date, int idUser) {
        Id = id;
        Name = name;
        Date = date;
        UserId = idUser;
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

}
