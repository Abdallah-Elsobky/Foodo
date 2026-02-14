package iti.student.foodo.features.favorites.presenter;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.data.repository.auth.AuthRepository;
import iti.student.foodo.data.repository.favorite.FavoriteRepository;
import iti.student.foodo.data.repository.planner.PlannerRepository;

public class FavPresenter implements FavContract.Presenter {

    private FavContract.View view;
    private final FavoriteRepository favoriteRepository;
    private final PlannerRepository plannerRepository;
    private final AuthRepository authRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public FavPresenter(FavoriteRepository favoriteRepository,
                        PlannerRepository plannerRepository, AuthRepository authRepository) {
        this.favoriteRepository = favoriteRepository;
        this.plannerRepository = plannerRepository;
        this.authRepository = authRepository;
    }


    @Override
    public void getFavorites() {
        Disposable disposable = favoriteRepository.getAllFavorites().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.showFavorites(meals)
                , throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void removeFavorite(String mealId) {
        Disposable disposable = favoriteRepository.removeFromFavorites(mealId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> Log.d("loco", "removed")
                , throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void addMealToPlanner(String date, String mealId) {
        if(authRepository.isGuest()){
            view.showError("You must be logged in to add a meal to your planner");
            return;
        }
        Disposable disposable = plannerRepository.addPlannedMeal(date, mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> Log.d("loco", "added")
                , throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(FavContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        compositeDisposable.clear();
    }
}
