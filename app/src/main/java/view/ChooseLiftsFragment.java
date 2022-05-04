package view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a02283751.workouttracker.R;

import viewmodel.ExerciseViewModel;

public class ChooseLiftsFragment extends Fragment {


    public ChooseLiftsFragment() {
        super(R.layout.fragment_choose_lifts);
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ExerciseViewModel viewModel = new ViewModelProvider(getActivity()).get(ExerciseViewModel.class);
        ObservableArrayList exercises = viewModel.getExercises();
        ObservableArrayList workoutExercises = viewModel.getWorkoutExercises();
        ChooseExerciseAdapter adapter = new ChooseExerciseAdapter(exercises, workoutExercises, viewModel);

        exercises.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        });

        view.findViewById(R.id.done_button_choose).setOnClickListener((v) ->{
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, WorkoutInformationFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        Button editSquat = view.findViewById(R.id.create_exercise_button);
        editSquat.setOnClickListener((view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, CreateLiftFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        }));


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);



    }



}
