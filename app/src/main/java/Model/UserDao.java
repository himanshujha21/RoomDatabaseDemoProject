package Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Query("select * from userdata")
    public UserData getUserData();


    @Insert
    void insert(UserData userData);

    @Query("DELETE FROM userdata")
    void deleteAll();

    @Update
    void update(UserData userData);
}



