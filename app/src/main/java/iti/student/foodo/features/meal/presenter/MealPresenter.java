package iti.student.foodo.features.meal.presenter;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.repository.MealRepository;

public class MealPresenter implements MealContract.Presenter {

    MealContract.View view;
    final MealRepository repository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public MealPresenter(MealRepository mealRepository) {
        this.repository = mealRepository;
    }

    @Override
    public void getMeals(String id) {
        Disposable disposable = repository.searchById(id).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meal -> {
                    if (view != null) {
                        view.onLoadMeals(meal.get(0));
                    }
                }
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void addToFavorites(String userId, String mealId) {
        repository.addToFavorites(userId, mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    Log.d("loco", "meal added: " + mealId);
                }
        );
    }

    @Override
    public void removeFromFavorites(String userId, String mealId) {

    }

    @Override
    public void isFavorite(String userId, String mealId) {

    }

    @Override
    public void attachView(MealContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        compositeDisposable.clear();
    }
}
