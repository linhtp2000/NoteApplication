package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appnote.User;

@Database(entities = (User.class), version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public static final String DatabaseName="user.db";
    private static   UserDatabase instance;
    public  static  synchronized  UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DatabaseName)
                    .allowMainThreadQueries()
                    .build();

        }
        return instance;
    }
        public abstract UserDAO userDAO();
    
}
