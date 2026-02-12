package iti.student.foodo.data.datasource.local;


import android.util.Log;

import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.data.db.AppDatabase;
import iti.student.foodo.data.db.dao.CartDao;
import iti.student.foodo.data.db.dao.FavoriteDao;
import iti.student.foodo.data.db.dao.IngredientDao;
import iti.student.foodo.data.db.dao.InstructionDao;
import iti.student.foodo.data.db.dao.MealDao;
import iti.student.foodo.data.db.dao.PlannedMealDao;
import iti.student.foodo.data.db.entity.CartIngredientEntity;
import iti.student.foodo.data.db.entity.FavoriteMealEntity;
import iti.student.foodo.data.db.entity.MealEntity;
import iti.student.foodo.data.db.entity.PlannedMealEntity;
import iti.student.foodo.data.db.pojo.MealWithDetails;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;
import iti.student.foodo.data.mapper.IngredientMapper;
import iti.student.foodo.data.mapper.InstructionMapper;
import iti.student.foodo.data.mapper.MealMapper;
import iti.student.foodo.data.model.domain.Meal;


public class MealLocalDataSource {

    private final MealDao mealDao;
    private final IngredientDao ingredientDao;
    private final InstructionDao instructionDao;
    private final FavoriteDao favoriteDao;
    private final CartDao cartDao;
    private final PlannedMealDao plannedMealDao;


    public MealLocalDataSource(AppDatabase db) {
        this.mealDao = db.mealDao();
        this.ingredientDao = db.ingredientDao();
        this.instructionDao = db.instructionDao();
        this.favoriteDao = db.favoriteDao();
        this.cartDao = db.cartDao();
        this.plannedMealDao = db.plannedMealDao();
    }


    public Flowable<List<MealEntity>> getAllMeals() {
        return mealDao.getAllMeals();
    }


    // Meal Dao
    public Completable saveMeals(List<MealEntity> meals) {
//        instructionDao.insertInstructions(meal.getId(),meal.getInstructions());
        return mealDao.insertMeals(meals);
    }

    public Completable saveMeal(Meal meal) {
        instructionDao.insertInstructions(InstructionMapper.toEntityList(meal.getInstructions()))
                .observeOn(Schedulers.io()).subscribe();
        ingredientDao.insertIngredients(IngredientMapper.toEntityList(meal.getIngredients()))
                .observeOn(Schedulers.io()).subscribe();
        // TODO TESTO
//        cartDao.insertCartItem(new CartIngredientEntity(meal.getIngredients().get(0).getName(), meal.getIngredients().get(0).getMeasure(), meal.getIngredients().get(0).getImage()))
//                .observeOn(Schedulers.io()).subscribe();
//        plannedMealDao.insertPlannedMeal(new PlannedMealEntity("12/12/2023", meal.getId()))
//                .observeOn(Schedulers.io()).subscribe();
        return mealDao.insertMeal(MealMapper.toEntity(meal));
    }

    public Completable clearMeals() {
        return mealDao.clearMeals();
    }


    public Flowable<MealWithDetails> getMealDetails(String mealId) {
        return mealDao.getMealWithDetails(mealId);
    }


    public Completable addToFavorites(String mealId) {
        return favoriteDao.addToFavorites(
                new FavoriteMealEntity(mealId, System.currentTimeMillis())
        );
    }


    // Meal Dao
    public Completable removeFromFavorites(String mealId) {
        return favoriteDao.removeFromFavorites(mealId);
    }

    public Flowable<Boolean> isFavorite(String mealId) {
        return favoriteDao.isFavorite(mealId);
    }

    public Flowable<List<MealWithDetails>> getUserFavorites() {
        return favoriteDao.getUserFavoriteMeals();
    }


    // Cart Dao
    public Completable addTOCart(CartIngredientEntity item) {
        return cartDao.insertCartItem(item);
    }

    public Completable removeItemFromCart(String ingredientName, String measure) {
        return cartDao.deleteCartItem(ingredientName, measure);
    }

    public Flowable<List<CartIngredientEntity>> getCartItems() {
        return cartDao.getCartItems();
    }

    public Completable clearCart() {
        return cartDao.clearCart();
    }

    // Planned Meal Dao
    public Completable addPlannedMeal(PlannedMealEntity plannedMeal) {
        return plannedMealDao.insertPlannedMeal(plannedMeal);
    }

    public Completable removePlannedMeal(String date, String mealId) {
        return plannedMealDao.deletePlannedMeal(date, mealId);
    }

    public Flowable<List<PlannedMealWithDetails>> getPlannedMeals(String date) {
        return plannedMealDao.getPlannedMealsWithDetails(date);
    }
}
