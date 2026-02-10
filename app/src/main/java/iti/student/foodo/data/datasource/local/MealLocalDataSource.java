package iti.student.foodo.data.datasource.local;


import android.util.Log;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.data.db.AppDatabase;
import iti.student.foodo.data.db.dao.FavoriteDao;
import iti.student.foodo.data.db.dao.IngredientDao;
import iti.student.foodo.data.db.dao.InstructionDao;
import iti.student.foodo.data.db.dao.MealDao;
import iti.student.foodo.data.db.entity.FavoriteMealEntity;
import iti.student.foodo.data.db.entity.MealEntity;
import iti.student.foodo.data.db.pojo.MealWithDetails;
import iti.student.foodo.data.mapper.Mapper;
import iti.student.foodo.data.model.domain.Meal;


public class MealLocalDataSource {

    private final MealDao mealDao;
    private final IngredientDao ingredientDao;
    private final InstructionDao instructionDao;
    private final FavoriteDao favoriteDao;

    public MealLocalDataSource(AppDatabase db) {
        this.mealDao = db.mealDao();
        this.ingredientDao = db.ingredientDao();
        this.instructionDao = db.instructionDao();
        this.favoriteDao = db.favoriteDao();
    }

    // ----------------- Meals -----------------

    public Flowable<List<MealEntity>> getAllMeals() {
        return mealDao.getAllMeals();
    }

    public Completable saveMeals(List<MealEntity> meals) {
//        instructionDao.insertInstructions(meal.getId(),meal.getInstructions());
        return mealDao.insertMeals(meals);
    }

    public Completable saveMeal(Meal meal) {
        instructionDao.insertInstructions(Mapper.toInstructionEntityList(meal.getInstructions()))
                .observeOn(Schedulers.io()).subscribe();
        ingredientDao.insertIngredients(Mapper.toIngredientEntityList(meal.getIngredients()))
                .observeOn(Schedulers.io()).subscribe();
        return mealDao.insertMeal(Mapper.toMealEntity(meal));
    }

    public Completable clearMeals() {
        return mealDao.clearMeals();
    }

    // ----------------- Meal Details -----------------

    public Flowable<MealWithDetails> getMealDetails(String mealId) {
        return mealDao.getMealWithDetails(mealId);
    }

    // ----------------- Favorites -----------------

    public Completable addToFavorites(String userId, String mealId) {
        return favoriteDao.addToFavorites(
                new FavoriteMealEntity(userId, mealId, System.currentTimeMillis())
        );
    }

    public Completable removeFromFavorites(String userId, String mealId) {
        return favoriteDao.removeFromFavorites(userId, mealId);
    }

    public Flowable<Boolean> isFavorite(String userId, String mealId) {
        return favoriteDao.isFavorite(userId, mealId);
    }

    public Flowable<List<MealWithDetails>> getUserFavorites(String userId) {
        return favoriteDao.getUserFavoriteMeals(userId);
    }
}
