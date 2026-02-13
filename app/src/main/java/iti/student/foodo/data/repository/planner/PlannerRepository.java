package iti.student.foodo.data.repository.planner;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.db.entity.PlannedMealEntity;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;

public interface PlannerRepository {

    Completable addPlannedMeal(String date, String mealId);

    Completable addPlannedMeal(PlannedMealEntity plannedMeal);

    Flowable<List<PlannedMealWithDetails>> getPlannedMeals(String date);

    Completable removePlannedMeal(String date, String mealId);

    Completable addPlannedMealToCloud(String mealId, String date);

    Single<List<PlannedMealEntity>> getPlannedMealsFromCloud();

    Completable removePlannedMealFromCloud(String mealId, String date);
}
