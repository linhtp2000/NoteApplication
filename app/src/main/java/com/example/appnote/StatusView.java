package com.example.appnote;

//Model dữ liệu hiện lên
public class StatusView {
//    public static int idStatus;
    public String name;
    public String Created;
    public int id;
    public StatusView(int id, String name, String created){
        this.id=id;
        this.name=name;
        this.Created =created;
    }
    public int getId(){
        return id;
    }
    public String getCreated() {
        return Created;
    }

    public String getName() {
        return name;
    }
    public void setId(int id){
        this.id=id;
    }

    public void setCreated(String created) {
        this.Created = created;
    }

    public void setName(String name) {
        this.name = name;
    }
}
