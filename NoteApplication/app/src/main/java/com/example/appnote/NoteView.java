package com.example.appnote;

public class NoteView {
    public int id;
    public String name;
    public String category;
    public String priority;
    public String status;
    public String planDate;
    public String createdday;
    public int cateid;
    public int prioid;
    public int statusid;
    public int iduser;
    public NoteView(int id,String name,  int cateid, String category, int prioid,String priority,int staid, String status, String createdday,String planDate,int iduser ){
        this.id=id;
        this.name=name;
        this.cateid=cateid;
        this.category=category;
        this.prioid=prioid;
        this.priority=priority;
        this.statusid=staid;
        this.status=status;
        this.planDate=planDate;
        this.createdday=createdday;
        this.iduser=iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getCreatedday() {
        return createdday;
    }

    public void setCreatedday(String createdday) {
        createdday = createdday;
    }
}
