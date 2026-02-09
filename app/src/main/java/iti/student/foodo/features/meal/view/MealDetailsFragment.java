package iti.student.foodo.features.meal.view;

import static iti.student.foodo.features.utils.VideoUtils.getVideoId;
import static iti.student.foodo.features.utils.VideoUtils.loadVideoPaused;
import static iti.student.foodo.features.utils.VideoUtils.pauseVideo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

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

public class MealDetailsFragment extends Fragment implements MealContract.View {

    // region Fields
    private FragmentMealDetailsBinding binding;
    private MealContract.Presenter presenter;
    // endregion

    // region Lifecycle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealPresenter(
                new MealRepositoryImpl(
                        new MealsRemoteDataSource(
                                ApiClient.getInstance().create(ApiService.class)
                        )
                )
        );
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        setupLoadingState();
        setupTabs();
        setupYoutubeLifecycle();
        setupBackButtonListener();
        requestMealDetails();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    // endregion

    // region UI Methods

    private void setupLoadingState() {
        binding.ingredientRv.setAdapter(
                new ShimmerAdapter(R.layout.ingredient_item)
        );
        binding.instructionsRv.setAdapter(
                new ShimmerAdapter(R.layout.instruction_item)
        );

        Objects.requireNonNull(binding.tabLayout.getTabAt(1)).select();
    }

    private void setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getPosition() == 0) {
                            pauseVideo(binding.youtubePlayerView);
                            Animations.slideOutToRight(binding.instructionsContainer);
                            Animations.slideInFromLeft(binding.ingredientRv);
                        } else {
                            Animations.slideInFromRight(binding.instructionsContainer);
                            Animations.slideOutToLeft(binding.ingredientRv);
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                }
        );
    }

    private void setupYoutubeLifecycle() {
        getLifecycle().addObserver(binding.youtubePlayerView);
    }

    private void setupBackButtonListener() {
        binding.btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }


    // endregion

    // region DATA
    private void requestMealDetails() {
        assert getArguments() != null;
        String mealId = MealDetailsFragmentArgs
                .fromBundle(getArguments())
                .getMealId();

        presenter.getMeals(mealId);

        Log.d("MealDetails", "meal id: " + mealId);
    }

    // endregion

    // region View Callbacks
    @Override
    public void onLoadMeals(Meal meal) {
        setupIngredients(meal);
        setupInstructions(meal);
        setupHeader(meal);
        setupVideo(meal);
    }

    private void setupIngredients(Meal meal) {
        MealIngredientAdapter ingredientAdapter = new MealIngredientAdapter(
                meal.getIngredients(),
                ingredient ->
                        Toast.makeText(
                                requireContext(),
                                "Added to cart",
                                Toast.LENGTH_SHORT
                        ).show()
        );
        binding.ingredientRv.setAdapter(ingredientAdapter);
    }

    private void setupInstructions(Meal meal) {
        MealInstructionAdapter instructionAdapter = new MealInstructionAdapter(meal.getInstructions());
        binding.instructionsRv.setAdapter(instructionAdapter);
    }

    private void setupHeader(Meal meal) {
        binding.tvMealTitle.setText(meal.getName());
        binding.chipArea.setText(meal.getArea());
        binding.chipCategory.setText(meal.getCategory());

        Glide.with(requireContext())
                .load(meal.getImage())
                .thumbnail(0.01f)
                .into(binding.imgMealHeader);
    }

    private void setupVideo(Meal meal) {
        loadVideoPaused(
                binding.youtubePlayerView,
                getVideoId(meal.getYoutube())
        );
    }

    // endregion

    // region State
    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String message) {
    }

    // endregion
}