package iti.student.foodo.features.favorites.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static iti.student.foodo.features.utils.BlurUtils.blurView;
import static iti.student.foodo.features.utils.BlurUtils.showBlur;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.db.AppDatabase;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.network.firebase.FirestoreService;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.data.repository.MealRepositoryImpl;
import iti.student.foodo.databinding.FragmentFavoritesBinding;
import iti.student.foodo.features.favorites.presenter.FavContract;
import iti.student.foodo.features.favorites.presenter.FavPresenter;
import iti.student.foodo.features.home.presenter.HomePresenter;
import iti.student.foodo.features.home.view.MealAdapter;
import iti.student.foodo.features.utils.ConfirmDialog;
import iti.student.foodo.features.utils.CustomDialog;
import iti.student.foodo.features.utils.CustomToastKt;
import iti.student.foodo.features.utils.MyDatePicker;

public class FavoritesFragment extends Fragment implements FavContract.View {

    FragmentFavoritesBinding binding;
    FavContract.Presenter presenter;
    private FragmentManager fragmentManager;
    private CustomDialog.OnDialogListener dialogListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FavPresenter(
                new MealRepositoryImpl(
                        new MealsRemoteDataSource(
                                ApiClient.getInstance().create(ApiService.class)),
                        new MealLocalDataSource(AppDatabase.getInstance(requireContext())),
                        new FirestoreService()));
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        presenter.getFavorites();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        presenter.detachView();
    }

    @Override
    public void showFavorites(List<Meal> meals) {
        if (meals.isEmpty()) {
            binding.noFavContainer.setVisibility(VISIBLE);
        } else {
            binding.noFavContainer.setVisibility(GONE);
        }
        binding.favCount.setText("" + meals.size());
        MealAdapter adapter = new MealAdapter(new MealAdapter.MealClickListener() {
            @Override
            public void onMealClick(Meal meal) {
                // Navigate to meal details
                NavDirections action = FavoritesFragmentDirections
                        .navigateToMealDetailsFragmentFromFavFragment(meal.getId());
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }

            @Override
            public void onFavoriteClick(Meal meal) {
                ConfirmDialog dialog = new ConfirmDialog(requireContext(),
                        "Remove Favorite",
                        "Are you sure you want to remove this item?",
                        new ConfirmDialog.OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                presenter.removeFavorite(meal.getId());
                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                dialog.show();
            }

            @Override
            public void onPlannerClick(Meal meal) {
                MyDatePicker datePicker = new MyDatePicker(requireContext(), date -> {
                    presenter.addMealToPlanner(date, meal.getId());
                    CustomToastKt.successToast(requireContext(), "Meal added to planner");
                });
                datePicker.show();
            }
        });
        adapter.submitList(meals);
        binding.favRv.setAdapter(adapter);
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