package iti.student.foodo.features.home.view;

import static android.view.View.*;

import static iti.student.foodo.features.utils.BlurUtils.*;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

import iti.student.foodo.R;
import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.core.utils.Animations;
import iti.student.foodo.core.utils.ShimmerAdapter;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.db.AppDatabase;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.network.firebase.FirestoreService;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.data.repository.MealRepositoryImpl;
import iti.student.foodo.databinding.FragmentHomeBinding;
import iti.student.foodo.features.home.presenter.HomeContract;
import iti.student.foodo.features.home.presenter.HomePresenter;
import iti.student.foodo.features.utils.CustomDialog;

public class HomeFragment extends Fragment implements HomeContract.View {

    private FragmentHomeBinding binding;
    private boolean isFirstTimeEnter = true;

    private CategoryAdapter categoriesAdapter;
    private MealAdapter mealAdapter;
    private HomeContract.Presenter presenter;

    private FragmentManager fragmentManager;
    private CustomDialog.OnDialogListener dialogListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(new MealRepositoryImpl(new MealsRemoteDataSource(ApiClient.getInstance().create(ApiService.class)),new MealLocalDataSource(AppDatabase.getInstance(requireContext())),new FirestoreService()));
        fragmentManager = getParentFragmentManager();
        dialogListener = new CustomDialog.OnDialogListener() {
            @Override
            public void onOpen() {
                blurView(binding.blurView, 30);
            }

            @Override
            public void onClosed() {
                showBlur(binding.blurView);
            }
        };
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
        presenter.getMeals();
        setupAdapters();
        setupRecyclerViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        if (isFirstTimeEnter) {
            startAnimations();
        }
    }

    private void setupAdapters() {
        categoriesAdapter = new CategoryAdapter(item -> {
            Log.d("Memo", "testo: " + item);
        });
        categoriesAdapter.submitList(Category.homeCategoryList());
        mealAdapter = new MealAdapter(new MealAdapter.MealClickListener() {
            @Override
            public void onMealClick(Meal meal) {
                NavDirections action = HomeFragmentDirections
                        .navigateToMealDetailsFragmentFromHomeFragment(meal.getId());
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }

            @Override
            public void onFavoriteClick(Meal meal) {

            }
        });
    }

    private void setupRecyclerViews() {
        if (isViewDestroyed()) return;
        binding.categoryRv.setAdapter(categoriesAdapter);
        binding.mealsRv.setAdapter(new ShimmerAdapter(R.layout.meal_item_shimmer));
    }

    private void startAnimations() {
        if (isViewDestroyed()) return;

        Animations.hintHorizontal(binding.categoryRv, () -> {
            if (isViewDestroyed()) return;
            Animations.hintHorizontal(binding.mealsRv, () -> {
            });
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
        isFirstTimeEnter = false;
        if (binding != null) {
            stopAnimations();
            presenter.detachView();
        }
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void showMeals(List<Meal> meals) {
        mealAdapter.submitList(meals);
//        Animations.smoothScrollTo(binding.scrollView, 400);
        binding.mealsRv.setAdapter(mealAdapter);
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Meal meal = meals.get(dayOfMonth % meals.size());
        showRandomMeal(meal);
    }

    @Override
    public void showRandomMeal(Meal meal) {
        binding.tvCategory.setText(meal.getCategory());
        binding.tvMealName.setText(meal.getName());
        binding.tvCountry.setText(meal.getArea());
        Glide.with(this).load(meal.getImage()).thumbnail(0.01f).into(binding.imgMealBackground);
        binding.mealOfTheDay.setOnClickListener(v -> {
            NavDirections action = HomeFragmentDirections
                    .navigateToMealDetailsFragmentFromHomeFragment(meal.getId());
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });
    }

    @Override
    public void showLoading() {
        binding.loading.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.loading.setVisibility(GONE);
    }

    @Override
    public void showError(String message) {
        CustomDialog.show(fragmentManager, "Error", message, dialogListener);
    }
}
