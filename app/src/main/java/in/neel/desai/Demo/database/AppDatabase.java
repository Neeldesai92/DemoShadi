package in.neel.desai.Demo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import in.neel.desai.Demo.Model.Result;

@Database(entities = {Result.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}