package Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class}, version = 1, exportSchema = false)

public abstract class UserDatabase extends RoomDatabase {


    /**
     * Disables the main thread query check for Room.
     * <p>
     * Room ensures that Database is never accessed on the main thread because it may lock the
     * main thread and trigger an ANR. If you need to access the database from the main thread,
     * you should always use async alternatives or manually move the call to a background
     * thread.
     * <p>
     * You may want to turn this check off for testing.
     **/

    private static volatile UserDatabase INSTANCE;

    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    // create the instance of database.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "User_Database").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();
}


