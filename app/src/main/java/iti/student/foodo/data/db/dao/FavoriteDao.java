package iti.student.foodo.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import iti.student.foodo.data.db.entity.FavoriteMealEntity;
import iti.student.foodo.data.db.pojo.MealWithDetails;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addToFavorites(FavoriteMealEntity favorite);

    @Query("DELETE FROM favorite_meals WHERE mealId = :mealId")
    Completable removeFromFavorites(String mealId);

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE mealId = :mealId)")
    Flowable<Boolean> isFavorite(String mealId);

    @Transaction
    @Query("SELECT meals.* FROM meals INNER JOIN favorite_meals ON meals.id = favorite_meals.mealId")
    Flowable<List<MealWithDetails>> getUserFavoriteMeals();

    @Query("DELETE FROM favorite_meals")
    Completable clearFavorites();
}

