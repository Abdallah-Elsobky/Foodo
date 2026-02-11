package iti.student.foodo.features.search.view;

import static iti.student.foodo.features.utils.BlurUtils.blurView;
import static iti.student.foodo.features.utils.BlurUtils.showBlur;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.util.List;
import java.util.stream.Collectors;

import iti.student.foodo.R;
import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.core.utils.ShimmerAdapter;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.db.AppDatabase;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.data.repository.MealRepositoryImpl;
import iti.student.foodo.databinding.FragmentSearchBinding;
import iti.student.foodo.features.home.view.CategoryAdapter;
import iti.student.foodo.features.home.view.MealAdapter;
import iti.student.foodo.features.search.presenter.SearchContract;
import iti.student.foodo.features.search.presenter.SearchPresenter;
import iti.student.foodo.features.utils.CustomDialog;

public class SearchFragment extends Fragment implements SearchContract.View {

    private FragmentSearchBinding binding;
    private SearchContract.Presenter presenter;

    private List<Category> allCategories;
    private List<Country> allCountries;
    private List<Ingredient> allIngredients;

    private MealAdapter mealAdapter;
    private CategoryAdapter categoryAdapter;
    private CountryAdapter countryAdapter;
    private IngredientAdapter ingredientAdapter;

    private FragmentManager fragmentManager;
    private CustomDialog.OnDialogListener dialogListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchPresenter(
                new MealRepositoryImpl(
                        new MealsRemoteDataSource(ApiClient.getInstance().create(ApiService.class)),
                        new MealLocalDataSource(AppDatabase.getInstance(requireContext()))
                )
        );
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        setupAdapters();
        setupRecyclerViews();
        setupChipListeners();
        setupSearchBar();
        presenter.getMealsBySearch("");
    }

    private void setupSearchBar() {
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase().trim();
                int currentChipId = binding.filterChipGroup.getCheckedChipId();

                if (currentChipId == R.id.chipCategory && allCategories != null) {
                    List<Category> filtered = allCategories.stream()
                            .filter(item -> item.getName().toLowerCase().contains(query))
                            .collect(Collectors.toList());
                    categoryAdapter.submitList(filtered);

                } else if (currentChipId == R.id.chipCountry && allCountries != null) {
                    List<Country> filtered = allCountries.stream()
                            .filter(item -> item.getName().toLowerCase().contains(query))
                            .collect(Collectors.toList());
                    countryAdapter.submitList(filtered);

                } else if (currentChipId == R.id.chipIngredient && allIngredients != null) {
                    List<Ingredient> filtered = allIngredients.stream()
                            .filter(item -> item.getName().toLowerCase().contains(query))
                            .collect(Collectors.toList());
                    ingredientAdapter.submitList(filtered);
                } else {
                    presenter.getMealsBySearch(query);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void setupAdapters() {
        categoryAdapter = new CategoryAdapter(item -> {
            presenter.getMealsByCategory(item);
            binding.mealRv.setAdapter(new ShimmerAdapter(R.layout.meal_item_shimmer));
        });
        countryAdapter = new CountryAdapter(item -> {
            presenter.getMealsByCountry(item);
            binding.mealRv.setAdapter(new ShimmerAdapter(R.layout.meal_item_shimmer));
        });
        ingredientAdapter = new IngredientAdapter(item -> {
            presenter.getMealsByIngredient(item);
            binding.mealRv.setAdapter(new ShimmerAdapter(R.layout.meal_item_shimmer));
        });

        mealAdapter = new MealAdapter(new MealAdapter.MealClickListener() {
            @Override
            public void onMealClick(Meal meal) {
                NavDirections action =
                        SearchFragmentDirections
                                .navigateToMealDetailsFragmentFromSearchFragment(meal.getId());

                Navigation.findNavController(binding.getRoot())
                        .navigate(action);
            }

            @Override
            public void onFavoriteClick(Meal meal) {
                // Handle favorite toggle
            }
        });
    }

    private void setupRecyclerViews() {


        binding.mealRv.setAdapter(new ShimmerAdapter(R.layout.meal_item_shimmer));

        binding.filterRv.setAdapter(new ShimmerAdapter(R.layout.filter_list_item_shimmer));
    }

    private void setupChipListeners() {
        binding.filterChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            binding.filterRv.setAdapter(new ShimmerAdapter(R.layout.filter_list_item_shimmer));
            if (checkedId == R.id.chipIngredient) {
                presenter.getIngredients();
            } else if (checkedId == R.id.chipCategory) {
                presenter.getCategories();
            } else if (checkedId == R.id.chipCountry) {
                presenter.getCountries();
            } else {
                // set empty list
                binding.filterRv.setAdapter(new CategoryAdapter(item -> {
                }));
            }
        });
        binding.filterChipGroup.check(R.id.chipName);
    }

    // region MVP View Methods

    @Override
    public void onLoadMeals(List<Meal> meals) {
        binding.mealRv.setAdapter(mealAdapter);
        mealAdapter.submitList(meals);
    }

    @Override
    public void onLoadCategories(List<Category> categories) {
        this.allCategories = categories;
        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter(item -> {
                presenter.getMealsByCategory(item);
            });
        }
        categoryAdapter.submitList(categories);
        binding.filterRv.setAdapter(categoryAdapter);
    }

    @Override
    public void onLoadCountries(List<Country> countries) {
        this.allCountries = countries;
        if (countryAdapter == null) {
            countryAdapter = new CountryAdapter(item -> {
                presenter.getMealsByCountry(item);
                binding.mealRv.setAdapter(new ShimmerAdapter(R.layout.meal_item_shimmer));
            });
        }
        countryAdapter.submitList(countries);
        binding.filterRv.setAdapter(countryAdapter);
    }

    @Override
    public void onLoadIngredients(List<Ingredient> ingredients) {
        this.allIngredients = ingredients;
        if (ingredientAdapter == null) {
            ingredientAdapter = new IngredientAdapter(item -> {
                presenter.getMealsByIngredient(item);
                binding.mealRv.setAdapter(new ShimmerAdapter(R.layout.meal_item_shimmer));
            });
        }
        ingredientAdapter.submitList(ingredients);
        binding.filterRv.setAdapter(ingredientAdapter);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String message) {
        CustomDialog.show(fragmentManager, "Error", message, dialogListener);
    }

    // endregion

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        binding = null;
    }
}