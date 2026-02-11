package iti.student.foodo.data.repository;


import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.db.entity.CartIngredientEntity;
import iti.student.foodo.data.db.entity.MealEntity;
import iti.student.foodo.data.db.pojo.MealWithDetails;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;
import iti.student.foodo.data.mapper.CategoryMapper;
import iti.student.foodo.data.mapper.CountryMapper;
import iti.student.foodo.data.mapper.IngredientMapper;
import iti.student.foodo.data.mapper.MealMapper;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.domain.Meal;

public class MealRepositoryImpl implements MealRepository {
    private final MealsRemoteDataSource remoteDataSource;
    private final MealLocalDataSource localDataSource;

    public MealRepositoryImpl(MealsRemoteDataSource remoteDataSource, MealLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public Single<List<Meal>> getMeals() {
        return remoteDataSource.getMeals()
                .compose(applySchedulers())
                .map(response -> MealMapper.mapList(response.getMeals()));
    }

    public Single<List<Meal>> getRandomMeal() {
        return remoteDataSource.getRandomMeal()
                .compose(applySchedulers())
                .map(response -> MealMapper.mapList(response.getMeals()));
    }

    public Single<List<Meal>> searchByName(String query) {
        return remoteDataSource.searchByName(query)
                .compose(applySchedulers())
                .map(response -> MealMapper.mapList(response.getMeals()));
    }

    public Single<List<Meal>> searchById(String query) {
        return remoteDataSource.searchById(query)
                .compose(applySchedulers())
                .map(response -> {
                    List<Meal> meals = MealMapper.mapList(response.getMeals());
                    // TODO move this into presenter to subscribe on it and check insertion success
                    localDataSource.saveMeal(meals.get(0)).observeOn(Schedulers.io()).subscribe();
                    return meals;
                });
    }

    // TODO TEST
    public Flowable<List<MealEntity>> getLocalMeals() {
        return localDataSource.getAllMeals();
    }

    // TODO TEST
    public Flowable<MealWithDetails> getLocalMeals(String mealId) {
        return localDataSource.getMealDetails(mealId).map(mealWithDetails -> {
            mealWithDetails.instructions.sort((a, b) -> a.instructionId - b.instructionId);
            mealWithDetails.ingredients.sort((a, b) -> a.ingredientId - b.ingredientId);
            return mealWithDetails;
        });
    }

    // TODO TEST
    public Completable addToFavorites(String userId, String mealId) {
        return localDataSource.addToFavorites(userId, mealId);
    }

    public Completable addTOCart(CartIngredientEntity item){
        return localDataSource.addTOCart(item);
    }

    // TODO TEST
    public Flowable<List<MealWithDetails>> getAllFavorites(String userId) {
        return localDataSource.getUserFavorites(userId);
    }

    public Flowable<List<PlannedMealWithDetails>> getPlannedMeals(String userId, String date) {
        return localDataSource.getPlannedMeals(userId, date);
    }

    public Completable removeFromFavorites(String userId, String mealId) {
        return localDataSource.removeFromFavorites(userId, mealId);
    }

    public Completable removePlannedMeal(String userId, String date, String mealId) {
        return localDataSource.removePlannedMeal(userId, date, mealId);
    }


    public Completable removeItemFromCart(String ingredientName, String measure) {
        return localDataSource.removeItemFromCart(ingredientName, measure);
    }


    public Single<List<Meal>> searchByCategory(String query) {
        return remoteDataSource.searchByCategory(query)
                .compose(applySchedulers())
                .map(response -> MealMapper.mapList(response.getMeals()));
    }

    public Single<List<Meal>> searchByArea(String query) {
        return remoteDataSource.searchByArea(query)
                .compose(applySchedulers())
                .map(response -> MealMapper.mapList(response.getMeals()));
    }

    public Single<List<Meal>> searchByIngredient(String query) {
        return remoteDataSource.searchByIngredient(query)
                .compose(applySchedulers())
                .map(response -> MealMapper.mapList(response.getMeals()));
    }

    @Override
    public Single<List<Category>> getCategories() {
        return remoteDataSource.getCategories()
                .map(s -> CategoryMapper.mapList(s.getCategories()))
                .compose(applySchedulers());
    }


    public Single<List<Country>> getAreas() {
        return remoteDataSource.getAreas()
                .map(s -> CountryMapper.mapList(s.getCountries()))
                .compose(applySchedulers());
    }

    public Single<List<Ingredient>> getIngredients() {
        return remoteDataSource.getIngredients()
                .map(s -> IngredientMapper.mapList(s.getIngredients()))
                .compose(applySchedulers());
    }

    private <T> SingleTransformer<T, T> applySchedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io());
    }
}
