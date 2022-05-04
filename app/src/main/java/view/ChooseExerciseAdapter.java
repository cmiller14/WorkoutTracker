package view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.a02283751.workouttracker.R;

import java.util.ArrayList;

import model.ExerciseModel;
import viewmodel.ExerciseViewModel;

public class ChooseExerciseAdapter extends RecyclerView.Adapter<ChooseExerciseAdapter.ViewHolder> {
    private ObservableArrayList<ExerciseModel> exercises;
    private ObservableArrayList<ExerciseModel> workoutExercises;
    private ExerciseViewModel viewModel;

    public ChooseExerciseAdapter(ObservableArrayList<ExerciseModel> exercises, ObservableArrayList<ExerciseModel> workoutExercises,
                                 ExerciseViewModel viewModel) {
        this.exercises = exercises;
        this.workoutExercises = workoutExercises;
        this.viewModel = viewModel;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_exersice_list_item, parent, false);
        return new ChooseExerciseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseModel exercise = exercises.get(position);
        TextView name = holder.itemView.findViewById(R.id.name_of_lift_choose);
        Button selectButton = holder.itemView.findViewById(R.id.choose_lift_button);
        Button removeButton = holder.itemView.findViewById(R.id.remove_lift_button);

        name.setText(exercise.name);

        selectButton.setOnClickListener(view -> {
            viewModel.addToWorkoutExercises(exercise);
        });

        removeButton.setOnClickListener(view -> {
            viewModel.removeFromWorkoutExercises(exercise);
        });


    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
