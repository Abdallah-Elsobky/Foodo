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
import iti.student.foodo.data.db.entity.PlannedMealEntity;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;
import iti.student.foodo.data.mapper.MealMapper;
import iti.student.foodo.data.network.firebase.FirestoreService;
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
                        AppDatabase.getInstance(requireContext())),
                new FirestoreService());
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
            repository.removePlannedMealFromCloud("53123","12/12/2006").subscribe(
                    () -> {
                        Log.d("loco", "remove planned Succeesss");
                    },
                    throwable -> {
                        Log.d("loco", "throwable: " + throwable);
                    }
            );

            repository.removeFavoriteFromCloud("53123").subscribe(
                    () -> {
                        Log.d("loco", "remove favorite Succeesss");
                    },
                    throwable -> {
                        Log.d("loco", "throwable: " + throwable);
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