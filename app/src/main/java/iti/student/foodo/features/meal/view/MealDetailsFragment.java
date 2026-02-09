package iti.student.foodo.features.meal.view;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import iti.student.foodo.R;
import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.core.utils.Animations;
import iti.student.foodo.core.utils.ShimmerAdapter;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.data.repository.MealRepositoryImpl;
import iti.student.foodo.databinding.FragmentMealDetailsBinding;
import iti.student.foodo.features.meal.presenter.MealContract;
import iti.student.foodo.features.meal.presenter.MealPresenter;
import iti.student.foodo.features.search.view.IngredientAdapter;

public class MealDetailsFragment extends Fragment implements MealContract.View {

    FragmentMealDetailsBinding binding;
    MealIngredientAdapter ingredientAdapter;
    MealInstructionAdapter instructionAdapter;
    MealContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealPresenter(new MealRepositoryImpl(new MealsRemoteDataSource(ApiClient.getInstance().create(ApiService.class))));
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ingredientRv.setAdapter(new ShimmerAdapter(R.layout.ingredient_item));
        binding.instructionsRv.setAdapter(new ShimmerAdapter(R.layout.instruction_item));
        Objects.requireNonNull(binding.tabLayout.getTabAt(1)).select();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    Animations.slideOutToRight(binding.instructionsRv);
                    Animations.slideInFromLeft(binding.ingredientRv);
                } else {
                    Animations.slideInFromRight(binding.instructionsRv);
                    Animations.slideOutToLeft(binding.ingredientRv);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        String mealId = MealDetailsFragmentArgs
                .fromBundle(getArguments())
                .getMealId();
        presenter.getMeals(mealId);
        Toast.makeText(requireContext(), mealId, Toast.LENGTH_SHORT).show();
        Log.d("memo", "meal id: " + mealId);
    }

    @Override
    public void onLoadMeals(Meal meal) {
        ingredientAdapter = new MealIngredientAdapter(meal.getIngredients(), ingredient -> {
            Toast.makeText(requireContext(), "added to cart", Toast.LENGTH_SHORT).show();
        });
        binding.ingredientRv.setAdapter(ingredientAdapter);
        instructionAdapter = new MealInstructionAdapter(meal.getInstructions());
        binding.instructionsRv.setAdapter(instructionAdapter);
        binding.tvMealTitle.setText(meal.getName());
        binding.chipArea.setText(meal.getArea());
        binding.chipCategory.setText(meal.getCategory());
        Glide.with(requireContext()).load(meal.getImage()).thumbnail(0.01f).into(binding.imgMealHeader);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}