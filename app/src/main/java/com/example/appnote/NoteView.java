package com.example.appnote;

public class NoteView {
    public int id;
    public String name;
    public String category;
    public String priority;
    public String status;
    public String planDate;
    public String Createdday;
    public NoteView(int id, String name, String category, String priority, String status, String planDate, String createdday){
        this.id=id;
        this.name=name;
        this.category=category;
        this.priority=priority;
        this.status=status;
        this.planDate=planDate;
        this.Createdday=createdday;
    }
    public void fixNote(int id, String name, String category, String priority, String status, String planDate){
        this.id=id;
        this.name=name;
        this.category=category;
        this.priority=priority;
        this.status=status;
        this.planDate=planDate;
//        this.Createdday=createdday;
    }
}
