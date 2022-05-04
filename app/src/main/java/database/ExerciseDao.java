package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import model.ExerciseModel;

@Dao
public interface ExerciseDao {
    @Insert
    public long insert(ExerciseModel exercise);

    @Query("SELECT * FROM exercisemodel")
    public List<ExerciseModel> getAll();

    @Query("SELECT * FROM exercisemodel WHERE id = :id LIMIT 1")
    public ExerciseModel findById(long id);

    @Update
    public void update(ExerciseModel exercise);

    @Delete
    public void delete(ExerciseModel exercise);

}
