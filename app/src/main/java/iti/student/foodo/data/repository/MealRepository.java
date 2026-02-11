package iti.student.foodo.data.repository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.db.entity.CartIngredientEntity;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.model.dto.CategoryResponse;
import iti.student.foodo.data.model.dto.CountryResponse;
import iti.student.foodo.data.model.dto.IngredientResponse;
import iti.student.foodo.data.model.dto.MealResponse;

public interface MealRepository {

    public Single<List<Meal>> getMeals();

    public Single<List<Meal>> getRandomMeal();

    public Single<List<Meal>> searchByName(String query);

    public Single<List<Meal>> searchById(String query);

    public Single<List<Meal>> searchByCategory(String query);

    public Single<List<Meal>> searchByArea(String query);

    public Single<List<Meal>> searchByIngredient(String query);

    public Single<List<Category>> getCategories();

    public Single<List<Country>> getAreas();

    public Single<List<Ingredient>> getIngredients();

    public Completable addToFavorites(String userId, String mealId);

    public Completable removeFromFavorites(String userId, String mealId);

    public Flowable<List<Meal>> getAllFavorites(String userId);

    public Completable addPlannedMeal(String userId, String date, String mealId);

    public Flowable<List<Meal>> getPlannedMeals(String userId, String date);

    public Completable removePlannedMeal(String userId, String date, String mealId);

    public Completable addTOCart(CartIngredientEntity item);

    public Flowable<List<CartIngredientEntity>> getCartItems();

    public Completable removeItemFromCart(String ingredientName, String measure);
}