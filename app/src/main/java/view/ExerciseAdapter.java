package view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.a02283751.workouttracker.R;

import java.util.ArrayList;

import model.ExerciseModel;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private ObservableArrayList<ExerciseModel> exercises;
    OnExerciseClicked listener;

    public interface OnExerciseClicked {
        public void onClicked(ExerciseModel exercise);
    }

    public ExerciseAdapter(ObservableArrayList<ExerciseModel> exercises, OnExerciseClicked listener) {
        this.exercises = exercises;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseModel exercise = exercises.get(position);
        TextView nameOfLift = holder.itemView.findViewById(R.id.lift_name_list);
        TextView repRange = holder.itemView.findViewById(R.id.rep_range_list);
        TextView weight = holder.itemView.findViewById(R.id.weight_list);

        nameOfLift.setText(exercise.name);
        repRange.setText(exercise.reps);
        weight.setText(exercise.weight);

        holder.itemView.setOnClickListener((view -> {
            listener.onClicked(exercises.get(position));
        }));


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
