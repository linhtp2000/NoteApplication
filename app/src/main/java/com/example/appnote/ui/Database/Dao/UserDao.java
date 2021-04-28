package com.example.appnote.ui.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appnote.ui.Database.Entity.Status;
import com.example.appnote.ui.Database.Entity.User;

import java.util.List;
@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
   public List<User> getAll();
    @Query("SELECT * FROM User WHERE Email = :e AND Password = :p")
   public User getUser(String e, String p);

    @Query("SELECT Id FROM User WHERE Email = :e")
    public int getId(String e);

    @Query("SELECT Email FROM User WHERE Email = :e")
    public String getEmail(String e);

    @Query("SELECT * FROM User WHERE Id = :id")
        public User getUserFromId(int id);

    @Insert
   public void insert(User user);

    @Update
   public void update(User user);
    @Delete
   public void delete(User user);


    @Delete
   public void delete(User... useres);
}
