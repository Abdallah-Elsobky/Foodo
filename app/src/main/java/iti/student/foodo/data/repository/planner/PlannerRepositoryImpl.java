package iti.student.foodo.data.repository.planner;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.db.entity.PlannedMealEntity;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;
import iti.student.foodo.data.network.firebase.FirestoreService;

public class PlannerRepositoryImpl implements PlannerRepository {

    private final MealLocalDataSource localDataSource;
    private final FirestoreService firestoreService;

    public PlannerRepositoryImpl(MealLocalDataSource localDataSource,
                                 FirestoreService firestoreService) {
        this.localDataSource = localDataSource;
        this.firestoreService = firestoreService;
    }

    @Override
    public Completable addPlannedMeal(String date, String mealId) {
        addPlannedMealToCloud(mealId, date).observeOn(AndroidSchedulers.mainThread()).subscribe();
        return localDataSource.addPlannedMeal(new PlannedMealEntity(date, mealId));
    }

    @Override
    public Completable addPlannedMeal(PlannedMealEntity plannedMeal) {
        addPlannedMealToCloud(plannedMeal.getMealId(), plannedMeal.getDate()).observeOn(AndroidSchedulers.mainThread()).subscribe();
        return localDataSource.addPlannedMeal(plannedMeal);
    }

    @Override
    public Flowable<List<PlannedMealWithDetails>> getPlannedMeals(String date) {
        return localDataSource.getPlannedMeals(date);
    }

    @Override
    public Completable removePlannedMeal(String date, String mealId) {
        removePlannedMealFromCloud(mealId, date).observeOn(AndroidSchedulers.mainThread()).subscribe();
        return localDataSource.removePlannedMeal(date, mealId);
    }

    @Override
    public Completable addPlannedMealToCloud(String mealId, String date) {
        return firestoreService.addPlannedMeal(mealId, date);
    }

    @Override
    public Single<List<PlannedMealEntity>> getPlannedMealsFromCloud() {
        return firestoreService.getPlannedMeals();
    }

    @Override
    public Completable removePlannedMealFromCloud(String mealId, String date) {
        return firestoreService.removePlannedMeal(mealId, date);
    }
}
