package view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.a02283751.workouttracker.R;
import com.google.android.material.button.MaterialButton;

import viewmodel.ExerciseViewModel;

public class EditLiftFragment extends Fragment {
    public EditLiftFragment() {
        super(R.layout.fragment_edit_lift);
    }
    private boolean previouslySaving = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExerciseViewModel viewModel = new ViewModelProvider(getActivity()).get(ExerciseViewModel.class);

        viewModel.getSaving().observe(getViewLifecycleOwner(), (saving) ->{
            if (saving && !previouslySaving) {
                Button button = view.findViewById(R.id.save_edit_button);
                button.setEnabled(false);
                button.setText("Saving...");
                previouslySaving = saving;
            } else if(previouslySaving && !saving) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        viewModel.getCurrentExercise().observe(getViewLifecycleOwner(), exercise -> {
            if(exercise == null) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                TextView name = view.findViewById(R.id.title_edit);
                EditText editWeight = view.findViewById(R.id.edit_weight_editText);
                EditText editReps = view.findViewById(R.id.edit_reps_editText);

                name.setText(exercise.name);
                editWeight.setText(exercise.weight);
                editReps.setText(exercise.reps);
            }
        });

        Button deleteButton = view.findViewById(R.id.delete_edit_button);
        Button saveButton = view.findViewById(R.id.save_edit_button);
        TextView name = view.findViewById(R.id.title_edit);
        EditText editWeight = view.findViewById(R.id.edit_weight_editText);
        EditText editReps = view.findViewById(R.id.edit_reps_editText);

        saveButton.setOnClickListener(view1 -> {
            viewModel.updateExerciseInfo(viewModel.getCurrentExercise().getValue(), name.getText().toString(), editReps.getText().toString(), editWeight.getText().toString());

        });


        deleteButton.setOnClickListener(view1 -> {
            viewModel.deleteCurrentExercise(viewModel.getCurrentExercise().getValue());
        });
    }
}
