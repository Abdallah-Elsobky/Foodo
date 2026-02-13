package iti.student.foodo.data.repository.favorite;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.mapper.MealMapper;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.network.firebase.FirestoreService;

public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final MealLocalDataSource localDataSource;
    private final FirestoreService firestoreService;

    public FavoriteRepositoryImpl(MealLocalDataSource localDataSource,
                                  FirestoreService firestoreService) {
        this.localDataSource = localDataSource;
        this.firestoreService = firestoreService;
    }

    @Override
    public Completable addToFavorites(String mealId) {
        addFavoriteToCloud(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe();
        return localDataSource.addToFavorites(mealId);
    }

    @Override
    public Completable removeFromFavorites(String mealId) {
        removeFavoriteFromCloud(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe();
        return localDataSource.removeFromFavorites(mealId);
    }

    @Override
    public Flowable<List<Meal>> getAllFavorites() {
        return localDataSource.getUserFavorites().map(MealMapper::fromEntityList);
    }

    @Override
    public Completable addFavoriteToCloud(String mealId) {
        return firestoreService.addFavorite(mealId);
    }

    @Override
    public Single<List<String>> getFavoritesFromCloud() {
        return firestoreService.getFavorites();
    }

    @Override
    public Completable removeFavoriteFromCloud(String mealId) {
        return firestoreService.removeFavorite(mealId);
    }
}
