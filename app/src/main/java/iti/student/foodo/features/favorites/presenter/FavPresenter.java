package iti.student.foodo.features.favorites.presenter;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.data.repository.MealRepository;

public class FavPresenter implements FavContract.Presenter {

    private FavContract.View view;
    private final MealRepository repository;
    private final String USER_ID = FirebaseAuth.getInstance().getUid();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public FavPresenter(MealRepository repository) {
        this.repository = repository;
    }


    @Override
    public void getFavorites() {
        Disposable disposable = repository.getAllFavorites().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.showFavorites(meals)
                , throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void removeFavorite(String mealId) {
        Disposable disposable = repository.removeFromFavorites(mealId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> Log.d("loco", "removed")
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
