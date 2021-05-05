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
import android.widget.TextView;
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
        TextView tvEmail_header= findViewById(R.id.tvEmail);
        Button btn_login = findViewById(R.id.btnLogin);
        EditText mail = (EditText) findViewById(R.id.txtEmai_login);
        EditText pass = (EditText) findViewById(R.id.txtpassword_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString().trim();
                String password = pass.getText().toString().trim();
                Boolean checklogin = login(email, password);
                if(email.equals("") || password.equals("") ) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(checklogin == true)
                {
                    int id=getIdFromEmail(mail.getText().toString());
                    MainActivity.IDCurrent=id;

                    startActivity(new Intent(MainActivity.this, ContentMainNav.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
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
        if(user!=null)
        {
            return true;
        }
        else return false;
    }

}