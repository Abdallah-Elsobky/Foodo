package iti.student.foodo.features.home;

import static iti.student.foodo.features.utils.BlurUtils.blurView;
import static iti.student.foodo.features.utils.CustomToastKt.successToast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.R;
import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.core.utils.Animations;
import iti.student.foodo.data.model.MainItem;
import iti.student.foodo.data.model.dto.CategoriesItem;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    List<MainItem> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDummy();
        MainItemAdapter categoriesAdapter = new MainItemAdapter();
        categoriesAdapter.submitList(categories);
        MainItemAdapter countryAdapter = new MainItemAdapter();
        countryAdapter.submitList(categories);
        Animations.hintHorizontal(binding.categoryRv, () -> {
            Animations.hintHorizontal(binding.countryRv, () -> {
            });
        });
        Animations.shakeView(binding.mealOfTheDay);

        binding.categoryRv.setAdapter(categoriesAdapter);
        binding.countryRv.setAdapter(countryAdapter);
    }


    void getDummy() {
        categories = List.of(
                new MainItem("Fish", "🐟"),
                new MainItem("Meat", "🥩"),
                new MainItem("Pasta", "🍝"),
                new MainItem("Salad", "🥗"),
                new MainItem("Dessert", "🍰"),
                new MainItem("Chicken", "🐔"),
                new MainItem("Pizza", "🍕"),
                new MainItem("Sushi", "🍣")
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}