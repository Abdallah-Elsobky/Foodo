package iti.student.foodo.data.repository;

import static iti.student.foodo.data.mapper.Mapper.*;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.mapper.Mapper;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.domain.Meal;

public class MealRepositoryImpl implements MealRepository {
    private MealsRemoteDataSource remoteDataSource;

    public MealRepositoryImpl(MealsRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public Single<List<Meal>> getMeals() {
        return remoteDataSource.getMeals()
                .compose(applySchedulers())
                .map(response -> mapList(response.getMeals()));
    }

    public Single<List<Meal>> getRandomMeal() {
        return remoteDataSource.getRandomMeal()
                .compose(applySchedulers())
                .map(response -> mapList(response.getMeals()));
    }

    public Single<List<Meal>> searchByName(String query) {
        return remoteDataSource.searchByName(query)
                .compose(applySchedulers())
                .map(response -> mapList(response.getMeals()));
    }

    public Single<List<Meal>> searchById(String query) {
        return remoteDataSource.searchById(query)
                .compose(applySchedulers())
                .map(response -> mapList(response.getMeals()));
    }

    public Single<List<Meal>> searchByCategory(String query) {
        return remoteDataSource.searchByCategory(query)
                .compose(applySchedulers())
                .map(response -> mapList(response.getMeals()));
    }

    public Single<List<Meal>> searchByArea(String query) {
        return remoteDataSource.searchByArea(query)
                .compose(applySchedulers())
                .map(response -> mapList(response.getMeals()));
    }

    public Single<List<Meal>> searchByIngredient(String query) {
        return remoteDataSource.searchByIngredient(query)
                .compose(applySchedulers())
                .map(response -> mapList(response.getMeals()));
    }

    @Override
    public Single<List<Category>> getCategories() {
        return remoteDataSource.getCategories()
                .map(s -> mapCategories(s.getCategories()))
                .compose(applySchedulers());
    }


    public Single<List<Country>> getAreas() {
        return remoteDataSource.getAreas()
                .map(s -> mapCountries(s.getCountries()))
                .compose(applySchedulers());
    }

    public Single<List<Ingredient>> getIngredients() {
        return remoteDataSource.getIngredients()
                .map(s -> mapIngredient(s.getIngredients()))
                .compose(applySchedulers());
    }

    private <T> SingleTransformer<T, T> applySchedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io());
    }
}
