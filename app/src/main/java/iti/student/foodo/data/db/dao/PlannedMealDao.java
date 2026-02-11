package iti.student.foodo.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import iti.student.foodo.data.db.entity.PlannedMealEntity;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;

@Dao
public interface PlannedMealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertPlannedMeal(PlannedMealEntity plannedMeal);

    @Query("DELETE FROM planned_meals WHERE userId = :userId AND date = :date AND mealId = :mealId")
    Completable deletePlannedMeal(
            String userId,
            String date,
            String mealId
    );
    @Transaction
    @Query("SELECT * FROM planned_meals WHERE userId = :userId AND date = :date")
    Flowable<List<PlannedMealWithDetails>> getPlannedMealsWithDetails(
            String userId,
            String date
    );
}
