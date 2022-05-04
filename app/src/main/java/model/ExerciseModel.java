package model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ExerciseModel {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String weight;

    @ColumnInfo
    public String reps;

    @ColumnInfo(name = "created_at")
    public long createdAt;

}
