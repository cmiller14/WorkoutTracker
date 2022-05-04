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

public class CreateLiftFragment extends Fragment {
    private boolean previouslySaving = false;
    public CreateLiftFragment() {
        super(R.layout.fragment_create_lift);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ExerciseViewModel viewModel = new ViewModelProvider(getActivity()).get(ExerciseViewModel.class);
        viewModel.getSaving().observe(getViewLifecycleOwner(), (saving) ->{
            if (saving && !previouslySaving) {
                Button button = view.findViewById(R.id.done_create);
                button.setEnabled(false);
                button.setText("Saving...");
                previouslySaving = saving;
            } else if(previouslySaving && !saving) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Button doneButton = view.findViewById(R.id.done_create);
        doneButton.setOnClickListener(view1 -> {
            EditText weight = view.findViewById(R.id.weight_num_create);
            EditText reps = view.findViewById(R.id.rep_num_create);
            TextView name = view.findViewById(R.id.lift_name_text_create);
            viewModel.saveExerciseInfo(name.getText().toString(), reps.getText().toString(), weight.getText().toString());
        });


    }
}
