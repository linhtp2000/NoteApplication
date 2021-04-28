package com.example.appnote;

//Model dữ liệu hiện lên
public class CategoryView {
    public String name;
    public int id;
    public String Createdday;
    public CategoryView(int id, String name, String createdday){
        this.id=id;
        this.name=name;
        this.Createdday=createdday;
    }
}
