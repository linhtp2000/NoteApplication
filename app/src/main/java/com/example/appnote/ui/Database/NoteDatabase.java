package com.example.appnote.ui.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appnote.ui.Database.Dao.CategoryDao;
import com.example.appnote.ui.Database.Dao.NoteDao;
import com.example.appnote.ui.Database.Dao.PriorityDao;
import com.example.appnote.ui.Database.Dao.StatusDao;
import com.example.appnote.ui.Database.Dao.UserDao;
import com.example.appnote.ui.Database.Entity.Category;
import com.example.appnote.ui.Database.Entity.Note;
import com.example.appnote.ui.Database.Entity.Priority;
import com.example.appnote.ui.Database.Entity.Status;
import com.example.appnote.ui.Database.Entity.User;

@Database(entities = {User.class, Category.class, Priority.class, Status.class, Note.class }, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract CategoryDao getCategoryDao();
    public abstract PriorityDao getPriorityDao();
    public abstract StatusDao getStatusDao();
    public abstract NoteDao getNoteDao();

    private static NoteDatabase noteDB;
    public static final String DB_NAME="NoteApp.db";

    public static NoteDatabase getInstance(Context context) {
        if (null == noteDB) {
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static synchronized NoteDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                NoteDatabase.class,
                DB_NAME)
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        noteDB = null;
    }

}
