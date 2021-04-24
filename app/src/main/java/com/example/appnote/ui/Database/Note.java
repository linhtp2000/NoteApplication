package com.example.appnote.ui.Database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    private String Name;
    public int CateId;
    @ForeignKey(
            entity = Category.class,
            parentColumns ="Id",
            childColumns = "CateId"
    )
   public int PrioId;
    @ForeignKey(
            entity = Priority.class,
            parentColumns ="Id",
            childColumns = "PrioId"
    )
    public int StatId;
    @ForeignKey(
            entity = Status.class,
            parentColumns ="Id",
            childColumns = "StatId"
    )
    private String Date;
    private String PlanDate;
    public int  UserId;
    @ForeignKey(
            entity = User.class,
            parentColumns ="Id",
            childColumns = "UserId"
    )

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

    public String getPlanDate() {
        return PlanDate;
    }

    public void setPlanDate(String planDate) {
        PlanDate = planDate;
    }

    public Note(int id, String name, int cateId, int prioId, int statId, String date, String planDate, int idUser) {
        Id = id;
        Name = name;
        CateId = cateId;
        PrioId = prioId;
        StatId = statId;
        Date = date;
        PlanDate = planDate;
        UserId = idUser;
    }

}

