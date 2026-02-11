package iti.student.foodo.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import iti.student.foodo.data.db.entity.MealEntity;
import iti.student.foodo.data.db.pojo.MealWithDetails;


@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeals(List<MealEntity> meals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealEntity meal);

    @Query("SELECT * FROM meals")
    Flowable<List<MealEntity>> getAllMeals();

    @Transaction
    @Query("SELECT * FROM meals WHERE id = :mealId")
    Flowable<MealWithDetails> getMealWithDetails(String mealId);

    @Query("DELETE FROM meals")
    Completable clearMeals();
}

