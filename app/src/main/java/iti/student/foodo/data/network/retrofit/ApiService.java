package iti.student.foodo.data.network.retrofit;

import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.model.dto.CategoryResponse;
import iti.student.foodo.data.model.dto.CountryResponse;
import iti.student.foodo.data.model.dto.IngredientResponse;
import iti.student.foodo.data.model.dto.MealResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search.php?s=")
    Single<MealResponse> getMeals();

    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("search.php")
    Single<MealResponse> searchByName(@Query("s") String query);

    @GET("lookup.php")
    Single<MealResponse> searchById(@Query("i") String query);

    @GET("filter.php")
    Single<MealResponse> searchByCategory(@Query("c") String query);

    @GET("filter.php")
    Single<MealResponse> searchByArea(@Query("a") String query);

    @GET("filter.php")
    Single<MealResponse> searchByIngredient(@Query("i") String query);

    @GET("categories.php")
    Single<CategoryResponse> getCategories();

    @GET("list.php?a=list")
    Single<CountryResponse> getAreas();

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredients();
}