package com.example.appnote;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appnote.ui.Database.Entity.User;
import com.example.appnote.ui.Database.NoteDatabase;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
   // public Database database;
    String StatusName;
    Cursor dataStatus;
    public static int IDCurrent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_form);
      //  database = new Database(this);

       // this.deleteDatabase("NoteApp.db");
        //  DatabaseHelper  helper= new DatabaseHelper(this);
        //  SQLiteDatabase db=helper.getReadableDatabase();
        //  InsertDataToDatabase();

        //Tạo DB
//        database=new Database(this,"note.sqlite",null,1);
//
//        database.QueryData("CREATE TABLE IF NOT EXISTS User(Id INTEGER PRIMARY KEY autoincrement,FirstName NVARCHAR,LastName nvarchar,EmailName NVARCHAR,PasswordName NVARCHAR)");
//        database.QueryData("CREATE TABLE IF NOT EXISTS Status(Id INTEGER PRIMARY KEY autoincrement,Name NVARCHAR,Created NVARCHAR, UserID INTERGER REFERENCES User (Id))");
//        database.QueryData("CREATE TABLE IF NOT EXISTS Priority(Id INTEGER PRIMARY KEY autoincrement ,Name NVARCHAR,Created NVARCHAR, UserID INTERGER REFERENCES User (Id))");
//        database.QueryData("CREATE TABLE IF NOT EXISTS Category(Id INTEGER PRIMARY KEY autoincrement,Name NVARCHAR,Created NVARCHAR, UserID INTERGER REFERENCES User (Id))");
//        database.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY autoincrement,Name NVARCHAR,CateID INTERGER REFERENCES Category (Id),PrioID INTERGER REFERENCES Priority (Id),StaID INTERGER REFERENCES Status (Id),Planday NVARCHAR,Created NVARCHAR, UserID INTERGER REFERENCES User (Id))");
////Insert dato to databse
//
//        database.QueryData("INSERT INTO Category VALUES("+ 1 +",'Working','2021-04-12 15:29:30',"+1+")");
//        database.QueryData("INSERT INTO Category VALUES("+ 2 +",'Relax','2021-04-12 15:29:30',"+1+")");
//        database.QueryData("INSERT INTO Category VALUES("+ 3 +",'Study','2021-04-12 15:29:30',"+1+")");
//
////        database.QueryData("INSERT INTO Category VALUES("+ 4 +",'Working','2021-04-12 15:29:30',"+2+")");
////        database.QueryData("INSERT INTO Category VALUES("+ 5 +",'Relax','2021-04-12 15:29:30',"+2+")");
////        database.QueryData("INSERT INTO Category VALUES("+ 6 +",'Study','2021-04-12 15:29:30',"+2+")");
//
//        database.QueryData("INSERT INTO Priority VALUES("+ 1 +",'High','2021-04-12 15:29:30',"+1+")");
//        database.QueryData("INSERT INTO Priority VALUES("+ 2 +",'Medium','2021-04-12 15:29:30',"+1+")");
//        database.QueryData("INSERT INTO Priority VALUES("+ 3 +",'Slow','2021-04-12 15:29:30',"+1+")");
//
//        database.QueryData("INSERT INTO Status VALUES("+ 1 +",'Done','2021-04-12 15:29:30',"+1+")");
//        database.QueryData("INSERT INTO Status VALUES("+ 2 +",'Processing','2021-04-12 15:29:30',"+1+")");
//        database.QueryData("INSERT INTO Status VALUES("+ 3 +",'Pending','2021-04-12 15:29:30',"+1+")");
//
//        database.QueryData("INSERT INTO Notes VALUES("+ 1 +",'Footbal',"+2+","+3+","+2+",'20/04/2021','2021-04-12 15:29:30',"+1+")");

        Button btn_login = findViewById(R.id.btnLogin);
        EditText mail = (EditText) findViewById(R.id.txtEmai_login);
        EditText pass = (EditText) findViewById(R.id.txtpassword_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString().trim();
                String password = pass.getText().toString().trim();
                Boolean checklogin = login(email, password);
                if(checklogin == true)
                {
                    int id=getIdFromEmail(mail.getText().toString());
                    MainActivity.IDCurrent=id;
                    startActivity(new Intent(MainActivity.this, ContentMainNav.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        Button btn_addAccount = findViewById(R.id.btnAddAccount);
        btn_addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterAccount.class));
            }
        });
        Button btn_exit = findViewById(R.id.btnExit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Thoát ứng dụng", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getEmail(String email)
    {
        return email;
    }
    private int getIdFromEmail(String email)
    {

        int id = NoteDatabase.getInstance(this).getUserDao().getId(email);
        return id;
    }
    public Boolean login(String email, String password)
    {

        User user= NoteDatabase.getInstance(this).getUserDao().getUser(email,password);
        if(user!=null) return true;
        else return false;
    }

}