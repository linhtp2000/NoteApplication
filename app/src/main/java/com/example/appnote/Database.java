package com.example.appnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "note.sqlite", null, 1);
    }

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Truy vấn không trả kết quả CREATE, INSERT, UPDATE, DELETE
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    //Truy vấn trả kq SELECT
    public Cursor GetData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Boolean insertData(Integer id, String email, String password, String firstName, String lastName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Id", id);
        contentValues.put("FirstName", firstName);
        contentValues.put("LastName", lastName);
        contentValues.put("EmailName", email);
        contentValues.put("PasswordName",password);
        long result = db.insert("User", null, contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public Boolean UpdateData(Integer id, String email, String firstName, String lastName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String s = Integer.toString(id);
        contentValues.put("FirstName", firstName);
        contentValues.put("LastName", lastName);
        contentValues.put("EmailName", email);
        Cursor cursor = db.rawQuery("Select * from User where Id= ?", new String[] {s});
        if(cursor.getCount() > 0)
        {
            long result = db.update("User", contentValues, "Id = ?", new String[] {s});
            if(result == -1){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    public Boolean UpdatePass(Integer id, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String s = Integer.toString(id);
        contentValues.put("PasswordName", password );
        Cursor cursor = db.rawQuery("Select * from User where Id= ?", new String[] {s});
        if(cursor.getCount() > 0)
        {
            long result = db.update("User", contentValues, "Id=?", new String[] {s});
            if(result == -1){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    public Boolean login(String email, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User where EmailName=? and PasswordName=?", new String[] {email, password});
        if(cursor.getCount() == 1) return true;
        else return false;
    }
    public Boolean checkMail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User where EmailName=?", new String[] {email});
        if(cursor.getCount()>0) return false;
        return true;
    }
    public Cursor getUser(Integer id)
    {
        String s = Integer.toString(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User where Id=?", new String[] {s});
        return cursor;
    }

    public Cursor getdata()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from User", null);
        return cursor;
    }
    public Boolean UpdateProfile(String email,String password, String firstName, String lastName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName", firstName);
        contentValues.put("LastName", lastName);
        contentValues.put("EmailName", email);
        contentValues.put("PasswordName",password);
        long result = db.insert("User", null, contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }
}
