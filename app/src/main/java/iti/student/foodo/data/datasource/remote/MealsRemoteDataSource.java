package iti.student.foodo.data.datasource.remote;

import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.model.dto.CategoryResponse;
import iti.student.foodo.data.model.dto.CountryResponse;
import iti.student.foodo.data.model.dto.IngredientResponse;
import iti.student.foodo.data.model.dto.MealResponse;
import iti.student.foodo.data.network.retrofit.ApiService;

public class MealsRemoteDataSource {
    private ApiService apiService;

    public MealsRemoteDataSource(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<MealResponse> getMeals() {
        return apiService.getMeals();
    }

    public Single<MealResponse> getRandomMeal() {
        return apiService.getRandomMeal();
    }

    public Single<MealResponse> searchByName(String query) {
        return apiService.searchByName(query);
    }

    public Single<MealResponse> searchById(String query) {
        return apiService.searchById(query);
    }

    public Single<MealResponse> searchByCategory(String query) {
        return apiService.searchByCategory(query);
    }

    public Single<MealResponse> searchByArea(String query) {
        return apiService.searchByArea(query);
    }

    public Single<MealResponse> searchByIngredient(String query) {
        return apiService.searchByIngredient(query);
    }

    public Single<CategoryResponse> getCategories() {
        return apiService.getCategories();
    }

    public Single<CountryResponse> getAreas() {
        return apiService.getAreas();
    }

    public Single<IngredientResponse> getIngredients() {
        return apiService.getIngredients();
    }
}
