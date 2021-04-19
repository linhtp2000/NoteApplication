package com.example.appnote;

//Model dữ liệu hiện lên
public class Status {
//    public static int idStatus;
    public String name;
    public String Created;
    public int id;
    public Status(int id,String name, String created){
        this.id=id;
        this.name=name;
        this.Created =created;
    }
}
