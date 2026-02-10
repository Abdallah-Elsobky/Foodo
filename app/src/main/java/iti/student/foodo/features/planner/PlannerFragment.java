package iti.student.foodo.features.planner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iti.student.foodo.R;
import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.db.AppDatabase;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.data.repository.MealRepositoryImpl;
import iti.student.foodo.databinding.FragmentPlannerBinding;

public class PlannerFragment extends Fragment {

    FragmentPlannerBinding binding;
    MealRepositoryImpl repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new MealRepositoryImpl(
                new MealsRemoteDataSource(
                        ApiClient.getInstance().create(ApiService.class)),
                new MealLocalDataSource(
                        AppDatabase.getInstance(requireContext())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // to test local
        binding.planner.setOnClickListener(v -> {
            repository.getAllFavorites("memo").subscribe(
                    mealWithDetails -> {
                        Log.d("loco", "meal size: " + mealWithDetails.size());
                        for (int i = 0; i < mealWithDetails.size(); i++) {
                            Log.d("loco", "meal name: " + mealWithDetails.get(i).meal.name);
                            Log.d("loco", "meal ingredients: " + mealWithDetails.get(i).ingredients.size());
                            Log.d("loco", "meal instructions: " + mealWithDetails.get(i).instructions.size());
                        }
                    }
            );
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}