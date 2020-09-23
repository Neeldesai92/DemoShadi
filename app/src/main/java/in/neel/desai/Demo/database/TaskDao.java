package in.neel.desai.Demo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import in.neel.desai.Demo.Model.Result;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Result")
    List<Result> getAll();

    @Insert
    void insert(Result task);

    @Delete
    void delete(Result task);

    @Update
    void update(Result task);



}