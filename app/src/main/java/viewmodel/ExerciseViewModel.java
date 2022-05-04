package viewmodel;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.ArrayList;

import database.AppDatabase;
import model.ExerciseModel;

public class ExerciseViewModel extends AndroidViewModel {
    private AppDatabase database;
    private MutableLiveData<Boolean> saving = new MutableLiveData<>();
    private ObservableArrayList<ExerciseModel> exercises = new ObservableArrayList<>();
    private Handler handler;
    private ObservableArrayList<ExerciseModel> workoutExercises = new ObservableArrayList<>();
    private MutableLiveData<ExerciseModel> currentExercise = new MutableLiveData<>();


    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        handler = new Handler();
        saving.setValue(false);
        database = Room.databaseBuilder(application, AppDatabase.class, "exercisedb").build();

        new Thread(() -> {
            ArrayList<ExerciseModel> exerciseModels = (ArrayList<ExerciseModel>)database.getExerciseDao().getAll();
            handler.post(() -> {
                exercises.addAll(exerciseModels);
            });
        }).start();
    }

    public MutableLiveData<ExerciseModel> getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentExercise(ExerciseModel currentExercise) {
        this.currentExercise.setValue(currentExercise);
    }

    public ObservableArrayList<ExerciseModel> getExercises() {
        return exercises;
    }

    public ObservableArrayList<ExerciseModel> getWorkoutExercises() {
        return workoutExercises;
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }

    public void deleteCurrentExercise(ExerciseModel exercise) {
        new Thread(() -> {
            database.getExerciseDao().delete(exercise);
            handler.post(() -> {
                exercises.remove(exercise);
                workoutExercises.remove(exercise);
            });
            currentExercise.postValue(null);
        }).start();

    }


    public void saveExerciseInfo(String name, String reps, String weight) {
        saving.setValue(true);
        new Thread(()-> {
            ExerciseModel exercise = new ExerciseModel();
            exercise.name = name;
            exercise.reps = reps;
            exercise.weight = weight;
            exercise.createdAt = System.currentTimeMillis();
            exercise.id = database.getExerciseDao().insert(exercise);
            //put into a list
            handler.post(() -> {
                exercises.add(exercise);
            });
            saving.postValue(false);
        }).start();

    }

    public void updateExerciseInfo(ExerciseModel exercise, String name, String reps, String weight ) {
        saving.setValue(true);
        new Thread(() -> {
            exercise.reps = reps;
            exercise.weight = weight;
            database.getExerciseDao().update(exercise);
            currentExercise.postValue(exercise);
            handler.post(() ->{
                int index = exercises.indexOf(exercise);
                workoutExercises.set(index, exercise);
                exercises.forEach(exerciseModel -> {
                    if (exerciseModel.id == exercise.id) {
                        exercises.set(exercises.indexOf(exerciseModel), exercise);
                    }
                });
            });
            saving.postValue(false);
        }).start();

    }

    public void addToWorkoutExercises(ExerciseModel exercise) {
        handler.post(()-> {
            workoutExercises.add(exercise);
        });
    }

    public void removeFromWorkoutExercises(ExerciseModel exercise) {
        handler.post(() -> {
            workoutExercises.remove(exercise);
        });
    }
}
