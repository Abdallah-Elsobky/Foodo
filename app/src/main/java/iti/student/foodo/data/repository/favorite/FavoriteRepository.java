package iti.student.foodo.data.repository.favorite;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.model.domain.Meal;

public interface FavoriteRepository {

    Completable addToFavorites(String mealId);

    Completable removeFromFavorites(String mealId);

    Flowable<List<Meal>> getAllFavorites();

    Completable addFavoriteToCloud(String mealId);

    Single<List<String>> getFavoritesFromCloud();

    Completable removeFavoriteFromCloud(String mealId);
}
