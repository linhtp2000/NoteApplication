package com.example.appnote.ui.Database.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(foreignKeys =
        {@ForeignKey(entity = User.class,
        parentColumns = "Id",
        childColumns = "UserId",
        onDelete = CASCADE,onUpdate = SET_NULL),
        @ForeignKey(entity = Category.class,
        parentColumns = "Id",
        childColumns = "CateId",
        onDelete = CASCADE,onUpdate = SET_NULL),
        @ForeignKey(entity = Priority.class,
        parentColumns = "Id",
        childColumns = "PrioId",
        onDelete = CASCADE,onUpdate = SET_NULL),
        @ForeignKey(entity = Status.class,
        parentColumns = "Id",
        childColumns = "StatId",
        onDelete = CASCADE,onUpdate = SET_NULL)
        })
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
    private int CateId;
    private int PrioId;
    private int StatId;

    private String Date;
    private String PlanDate;
    private int UserId;

    public Note(){}
    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getUserId() {
        return UserId;
    }

    public int getCateId() {
        return CateId;
    }

    public int getPrioId() {
        return PrioId;
    }

    public int getStatId() {
        return StatId;
    }

    public void setCateId(int cateId) {
        CateId = cateId;
    }

    public void setPrioId(int prioId) {
        PrioId = prioId;
    }

    public void setStatId(int statId) {
        StatId = statId;
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
    public Note(String name, int cateId, int prioId, int statId, String date, String planDate, int idUser) {
        Name = name;
        CateId = cateId;
        PrioId = prioId;
        StatId = statId;
        Date = date;
        PlanDate = planDate;
        UserId = idUser;
    }
    public Note(String name, int cateId, int prioId, int statId, String date, int idUser) {
        Name = name;
        CateId = cateId;
        PrioId = prioId;
        StatId = statId;
        Date = date;
        UserId = idUser;
    }
}

