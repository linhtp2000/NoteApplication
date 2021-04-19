package database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appnote.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void  insertUser(User user);
    @Query("Select * from user")
    List<User> getListUser();

}
