package iti.student.foodo.features.home.presenter;


import static iti.student.foodo.features.utils.ErrorUtils.*;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.repository.MealRepository;

public class HomePresenter implements HomeContract.Presenter {
    HomeContract.View view;
    MealRepository repository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomePresenter(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attachView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
        view = null;
    }

    @Override
    public void getMeals() {
        if (view != null) view.showLoading();
        Disposable disposable = repository.getMeals()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showMeals(meals);
                                Log.d("Memo", "meals size: " + meals.size());
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showError(getErrorMessage(throwable));
                            }
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void getRandomMeal() {
        if (view != null) view.showLoading();
        Disposable disposable = repository.getRandomMeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showMeals(meals);
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showError(getErrorMessage(throwable));
                            }
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void addFavorite(String mealId) {
        repository.addToFavorites(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    repository.searchById(mealId).subscribe();
                    Log.d("loco", "meal added: " + mealId);
                }
                , throwable -> Log.d("loco", "error: " + throwable.getMessage())
        );
    }

    @Override
    public void removeFavorite(String mealId) {
        repository.removeFromFavorites(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    Log.d("loco", "meal removed: " + mealId);
                }
                , throwable -> Log.d("loco", "error: " + throwable.getMessage())
        );
    }
}
