package com.example.appnote;

public class UserView {
    public int id;
    public String email;
    public String password;
    public String firstname;
    public String lastname;
    public UserView(){}
    public UserView(int id, String email, String password, String firstname, String lastname)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public UserView(String email, String password)
    {
        this.email=email;
        this.password=password;
    }

}
