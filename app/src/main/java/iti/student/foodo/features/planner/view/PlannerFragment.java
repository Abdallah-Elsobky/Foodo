package iti.student.foodo.features.planner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import iti.student.foodo.R;
import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.db.AppDatabase;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.network.firebase.FirestoreService;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.data.repository.meal.MealRepositoryImpl;
import iti.student.foodo.databinding.FragmentPlannerBinding;
import iti.student.foodo.features.planner.presenter.PlannerContract;
import iti.student.foodo.features.planner.presenter.PlannerPresenter;
import iti.student.foodo.features.utils.ConfirmDialog;

public class PlannerFragment extends Fragment implements PlannerContract.View {

    FragmentPlannerBinding binding;
    MealRepositoryImpl repository;
    PlannerContract.Presenter presenter;
    String selectedDay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlannerPresenter(repository = new MealRepositoryImpl(
                new MealsRemoteDataSource(
                        ApiClient.getInstance().create(ApiService.class)),
                new MealLocalDataSource(
                        AppDatabase.getInstance(requireContext())),
                new FirestoreService()));
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
        presenter.attachView(this);
        setupCalendarView();
        setupClickListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        binding = null;
    }

    private String formatSelectedDate(int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    private String formatMonthAndDay(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d", Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    private void setupCalendarView() {
        CalendarView.OnDateChangeListener listener = (view, year, month, dayOfMonth) -> {
            String formattedDate = formatSelectedDate(year, month, dayOfMonth);
            selectedDay = formattedDate;
            binding.dateName.setText(formatMonthAndDay(year, month, dayOfMonth));
            presenter.getMeals(formattedDate);
        };
        binding.calenderView.setOnDateChangeListener(listener);
        Calendar todayCal = Calendar.getInstance();
        int year = todayCal.get(Calendar.YEAR);
        int month = todayCal.get(Calendar.MONTH);
        int day = todayCal.get(Calendar.DAY_OF_MONTH);
        binding.calenderView.setDate(todayCal.getTimeInMillis(), false, true);
        listener.onSelectedDayChange(binding.calenderView, year, month, day);
    }


    private void setupClickListeners() {
        binding.addBtn.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.plannerFragment, true)
                    .build();
            navController.navigate(R.id.searchFragment, null, navOptions);
        });
    }

    @Override
    public void showMeals(List<PlannedMealWithDetails> meals) {
        if (meals.isEmpty())
            binding.noMealsContainer.setVisibility(View.VISIBLE);
        else
            binding.noMealsContainer.setVisibility(View.GONE);
        binding.mealCount.setText(meals.size() + "");
        DeleteMealAdapter adapter = new DeleteMealAdapter(new DeleteMealAdapter.MealClickListener() {
            @Override
            public void onMealClick(Meal meal) {
                NavDirections action = PlannerFragmentDirections
                        .navigateToMealDetailsFragmentFromPlannerFragment(meal.getId());
                Navigation.findNavController(binding.getRoot()).navigate(action);
            }

            @Override
            public void onFavoriteClick(Meal meal) {
                ConfirmDialog dialog = new ConfirmDialog(requireContext(),
                        "Remove Planned Meal",
                        "Are you sure you want to remove this item?",
                        new ConfirmDialog.OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                presenter.removeMeal(selectedDay, meal.getId());
                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                dialog.show();
            }
        });
        adapter.submitList(meals);
        binding.mealRv.setAdapter(adapter);
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