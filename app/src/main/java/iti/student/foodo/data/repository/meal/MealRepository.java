package iti.student.foodo.data.repository.meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.domain.Meal;

public interface MealRepository {

    Single<List<Meal>> getMeals();

    Single<List<Meal>> getRandomMeal();

    Single<List<Meal>> searchByName(String query);

    Single<List<Meal>> searchById(String query);

    Single<List<Meal>> searchByCategory(String query);

    Single<List<Meal>> searchByArea(String query);

    Single<List<Meal>> searchByIngredient(String query);

    Single<List<Category>> getCategories();

    Single<List<Country>> getAreas();

    Single<List<Ingredient>> getIngredients();

    Flowable<Meal> getLocalMeals(String mealId);

    Completable saveMeals(List<Meal> meals);

    Completable dropMeals();
}
