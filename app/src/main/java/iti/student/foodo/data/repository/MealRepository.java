package iti.student.foodo.data.repository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.model.domain.Category;
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

    public Single<CountryResponse> getAreas();

    public Single<IngredientResponse> getIngredients();
}
