package iti.student.foodo.features.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.core.utils.Animations;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.data.repository.MealRepositoryImpl;
import iti.student.foodo.databinding.FragmentHomeBinding;
import iti.student.foodo.features.home.presenter.HomeContract;
import iti.student.foodo.features.home.presenter.HomePresenter;

public class HomeFragment extends Fragment implements HomeContract.View {

    private FragmentHomeBinding binding;

    private homeCategoryAdapter categoriesAdapter;
    private homeCategoryAdapter countryAdapter;
    private HomeContract.Presenter presenter;

    ApiService api = ApiClient.getInstance().create(ApiService.class);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(new MealRepositoryImpl(new MealsRemoteDataSource(ApiClient.getInstance().create(ApiService.class))));
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getRandomMeal();
        binding.titleOfDay.setOnClickListener(v -> {
            api.searchByCategory("Seafood").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ingredients -> {
                                Log.d("Memo", "Ingredients: " + ingredients.getMeals());
                            },
                            throwable -> {
                                Log.d("Memo", "Error: " + throwable.getMessage());
                            }
                    );
        });
        setupAdapters();
        setupRecyclerViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        startAnimations();
    }

    private void setupAdapters() {
        categoriesAdapter = new homeCategoryAdapter();
        categoriesAdapter.submitList(Category.homeCategoryList());

        countryAdapter = new homeCategoryAdapter();
    }

    private void setupRecyclerViews() {
        if (binding == null) return;

        binding.categoryRv.setAdapter(categoriesAdapter);
    }

    private void startAnimations() {
        if (isViewDestroyed()) return;

        Animations.hintHorizontal(binding.categoryRv, () -> {
        });

        Animations.shakeView(binding.mealOfTheDay);
    }

    private void stopAnimations() {
        binding.categoryRv.animate().cancel();
        binding.mealOfTheDay.animate().cancel();
    }

    private boolean isViewDestroyed() {
        return !isAdded() || binding == null || getView() == null;
    }

    @Override
    public void onDestroyView() {
        if (binding != null) {
            stopAnimations();
            presenter.detachView();
        }
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void showMeals(List<Meal> meals) {
        Meal meal = meals.get(0);
        showRandomMeal(meal);
    }

    @Override
    public void showRandomMeal(Meal meal) {
        binding.mealCategory.setText(meal.getCategory());
        binding.mealName.setText(meal.getName());
        Glide.with(this).load(meal.getImage()).into(binding.imageVector);
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
}
