package com.example.appnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterAccount extends AppCompatActivity {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnview = (Button) findViewById(R.id.btnSignin_register);
        EditText editMail = (EditText) findViewById(R.id.txtEmail_register);
        EditText editPassword = (EditText) findViewById(R.id.txtpassword_register);
        EditText editConfirmPassword = (EditText) findViewById(R.id.txtrepeatepass_register);
        db = new Database(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editMail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String confirm = editConfirmPassword.getText().toString().trim();
                int id = 0;
                if(password.equals(confirm))
                {
                    Boolean checkmail = db.checkMail(email);
                    if(checkmail == true)
                    {
                        Boolean checkInsert =  db.insertData(null, email, password, null, null);
                        if(checkInsert == true)
                        {
//                            int i=getIdFromEmail(email.toString());
//                            MainActivity.IDCurrent=id;
                            Toast.makeText(RegisterAccount.this, "Đã thêm xong rồi nhé!", Toast.LENGTH_SHORT).show();
                     //       startActivity(new Intent(RegisterAccount.this, ContentMainNav.class));
                        }
                        else {
                            Toast.makeText(RegisterAccount.this, "OMG! Không thêm được nhé!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterAccount.this, "OMG! Trùng email rồi nhé!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterAccount.this, "Password và Confirm password không trùng khớp", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterAccount.this, MainActivity.class));
            }
        });
    }
    private int getIdFromEmail(String email)
    {
        String query = "SELECT * FROM User WHERE EmailName= '"+email+"';";
        SQLiteDatabase DB = db.getWritableDatabase();
        Cursor cursor = DB.rawQuery(query, null);
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            break;
        }
        cursor.close();
        db.close();
        return id;
    }
}