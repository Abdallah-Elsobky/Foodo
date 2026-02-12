package iti.student.foodo.data.repository;

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
import iti.student.foodo.data.db.entity.CartIngredientEntity;
import iti.student.foodo.data.db.entity.MealEntity;
import iti.student.foodo.data.db.entity.PlannedMealEntity;
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
import iti.student.foodo.data.network.firebase.FirestoreService;

public class MealRepositoryImpl implements MealRepository {

    private final MealsRemoteDataSource remoteDataSource;
    private final MealLocalDataSource localDataSource;
    private final FirestoreService firestoreService;

    public MealRepositoryImpl(MealsRemoteDataSource remoteDataSource,
                              MealLocalDataSource localDataSource, FirestoreService firestoreService) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.firestoreService = firestoreService;
    }

    /* ============================
       Remote - Meals
     ============================ */

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


    private Single<List<Meal>> mergeWithFavorites(
            Single<List<Meal>> mealsSingle
    ) {

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

    public Single<List<Meal>> searchByName(String query) {

        Single<List<Meal>> meals =
                remoteDataSource.searchByName(query)
                        .map(response -> MealMapper.mapList(response.getMeals()));
        return mergeWithFavorites(meals)
                .compose(applySchedulers());
    }

    public Single<List<Meal>> searchByCategory(String query) {
        Single<List<Meal>> meals =
                remoteDataSource.searchByCategory(query)
                        .map(response -> MealMapper.mapList(response.getMeals()));
        return mergeWithFavorites(meals)
                .compose(applySchedulers());
    }


    public Single<List<Meal>> searchByArea(String query) {
        Single<List<Meal>> meals =
                remoteDataSource.searchByArea(query)
                        .map(response -> MealMapper.mapList(response.getMeals()));
        return mergeWithFavorites(meals)
                .compose(applySchedulers());
    }


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

    /* ============================
       Local - Meals
     ============================ */

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

    public Completable saveMeals(List<Meal> meals) {
        return localDataSource.saveMeals(MealMapper.toEntityList(meals));
    }

    /* ============================
       Favorites
     ============================ */

    public Completable addToFavorites(String mealId) {
        return localDataSource.addToFavorites(mealId);
    }

    public Completable removeFromFavorites(String mealId) {
        return localDataSource.removeFromFavorites(mealId);
    }

    public Flowable<List<Meal>> getAllFavorites() {
        return localDataSource.getUserFavorites().map(MealMapper::fromEntityList);
    }

    /* ============================
       Planner
     ============================ */

    public Completable addPlannedMeal(PlannedMealEntity plannedMeal) {
        return localDataSource.addPlannedMeal(plannedMeal);
    }


    @Override
    public Completable addPlannedMeal(String date, String mealId) {
        return localDataSource.addPlannedMeal(new PlannedMealEntity(date, mealId));
    }

    @Override
    public Flowable<List<PlannedMealWithDetails>> getPlannedMeals(String date) {
        return localDataSource.getPlannedMeals(date);
    }

//    public Flowable<List<Meal>> getPlannedMeals(String userId, String date) {
//        return localDataSource.getPlannedMeals(userId, date).map(MealMapper::fromEntityList);
//    }

    public Completable removePlannedMeal(String date, String mealId) {
        return localDataSource.removePlannedMeal(date, mealId);
    }

    /* ============================
       Cart
     ============================ */

    public Completable addTOCart(CartIngredientEntity item) {
        return localDataSource.addTOCart(item);
    }

    public Completable removeItemFromCart(String ingredientName,
                                          String measure) {
        return localDataSource.removeItemFromCart(ingredientName, measure);
    }

    public Flowable<List<CartIngredientEntity>> getCartItems() {
        return localDataSource.getCartItems();
    }


    // Test

    public Completable addFavoriteToCloud(String mealId) {
        return firestoreService.addFavorite(mealId);
    }

    public Single<List<String>> getFavoritesFromCloud() {
        return firestoreService.getFavorites();
    }

    public Completable removeFavoriteFromCloud(String mealId) {
        return firestoreService.removeFavorite(mealId);
    }

    public Completable addPlannedMealToCloud(String mealId, String date) {
        return firestoreService.addPlannedMeal(mealId, date);
    }

    public Single<List<PlannedMealEntity>> getPlannedMealsFromCloud() {
        return firestoreService.getPlannedMeals();
    }

    public Completable removePlannedMealFromCloud(String mealId, String date) {
        return firestoreService.removePlannedMeal(mealId, date);
    }



    /* ============================
       Scheduler Helper
     ============================ */

    private <T> SingleTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io());
    }
}
