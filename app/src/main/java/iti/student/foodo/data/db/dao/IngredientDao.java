package iti.student.foodo.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import iti.student.foodo.data.db.entity.IngredientEntity;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredients WHERE mealId = :mealId ORDER BY ingredientId ASC")
    Flowable<List<IngredientEntity>> getIngredientsForMeal(String mealId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertIngredients(List<IngredientEntity> ingredients);

    @Query("DELETE FROM ingredients")
    Completable clearIngredients();
}

