package iti.student.foodo.data.repository.meal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
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

    public MealRepositoryImpl(MealsRemoteDataSource remoteDataSource,
                              MealLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    /* ============================
       Remote - Meals
     ============================ */

    @Override
    public Single<List<Meal>> getMeals() {
        return remoteDataSource.getMeals()
                .compose(applySchedulers())
                .map(response -> MealMapper.mapList(response.getMeals()))
                .flatMap(meals -> saveMeals(meals)
                        .andThen(Single.just(meals)));
    }

    @Override
    public Single<List<Meal>> getRandomMeal() {
        return remoteDataSource.getRandomMeal()
                .compose(applySchedulers())
                .map(response -> MealMapper.mapList(response.getMeals()));
    }

    @Override
    public Single<List<Meal>> searchById(String query) {
        return remoteDataSource.searchById(query)
                .compose(applySchedulers())
                .map(response -> {
                    List<Meal> meals = MealMapper.mapList(response.getMeals());
                    localDataSource.saveMeal(meals.get(0))
                            .observeOn(Schedulers.io())
                            .subscribe();
                    return meals;
                });
    }

    @Override
    public Single<List<Meal>> searchByName(String query) {
        Single<List<Meal>> meals =
                remoteDataSource.searchByName(query)
                        .map(response -> MealMapper.mapList(response.getMeals()));
        return mergeWithFavorites(meals)
                .compose(applySchedulers());
    }

    @Override
    public Single<List<Meal>> searchByCategory(String query) {
        Single<List<Meal>> meals =
                remoteDataSource.searchByCategory(query)
                        .map(response -> MealMapper.mapList(response.getMeals()));
        return mergeWithFavorites(meals)
                .compose(applySchedulers());
    }

    @Override
    public Single<List<Meal>> searchByArea(String query) {
        Single<List<Meal>> meals =
                remoteDataSource.searchByArea(query)
                        .map(response -> MealMapper.mapList(response.getMeals()));
        return mergeWithFavorites(meals)
                .compose(applySchedulers());
    }

    @Override
    public Single<List<Meal>> searchByIngredient(String query) {
        Single<List<Meal>> meals =
                remoteDataSource.searchByIngredient(query)
                        .map(response -> MealMapper.mapList(response.getMeals()));
        return mergeWithFavorites(meals)
                .compose(applySchedulers());
    }

    /* ============================
       Remote - Static Data
     ============================ */

    @Override
    public Single<List<Category>> getCategories() {
        return remoteDataSource.getCategories()
                .map(s -> CategoryMapper.mapList(s.getCategories()))
                .compose(applySchedulers());
    }

    @Override
    public Single<List<Country>> getAreas() {
        return remoteDataSource.getAreas()
                .map(s -> CountryMapper.mapList(s.getCountries()))
                .compose(applySchedulers());
    }

    @Override
    public Single<List<Ingredient>> getIngredients() {
        return remoteDataSource.getIngredients()
                .map(s -> IngredientMapper.mapList(s.getIngredients()))
                .compose(applySchedulers());
    }

    /* ============================
       Local - Meals
     ============================ */

    @Override
    public Flowable<Meal> getLocalMeals(String mealId) {
        return localDataSource.getMealDetails(mealId)
                .map(mealWithDetails -> {
                    mealWithDetails.instructions
                            .sort((a, b) -> a.instructionId - b.instructionId);
                    mealWithDetails.ingredients
                            .sort((a, b) -> a.ingredientId - b.ingredientId);
                    return mealWithDetails;
                }).map(MealMapper::fromEntity);
    }

    @Override
    public Flowable<List<Meal>> getLocalMeals() {
        return localDataSource.getAllMeals().map(MealMapper::fromMealEntityList);
    }

    @Override
    public Completable saveMeals(List<Meal> meals) {
        return localDataSource.saveMeals(MealMapper.toEntityList(meals));
    }

    @Override
    public Completable dropMeals() {
        return localDataSource.dropMeals();
    }

    /* ============================
       Helper Methods
     ============================ */

    private Single<List<Meal>> mergeWithFavorites(Single<List<Meal>> mealsSingle) {
        return Single.zip(
                mealsSingle,
                localDataSource.getUserFavorites()
                        .first(new ArrayList<>())
                        .map(MealMapper::fromEntityList),
                (meals, favoriteMeals) -> {
                    Set<String> favIds = new HashSet<>();
                    for (Meal fav : favoriteMeals) {
                        favIds.add(fav.getId());
                    }
                    for (Meal meal : meals) {
                        meal.setFav(favIds.contains(meal.getId()));
                    }
                    return meals;
                }
        );
    }

    private <T> SingleTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io());
    }
}
