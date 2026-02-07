package iti.student.foodo.features.home.presenter;


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
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showError(throwable.getMessage());
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
                                view.showError(throwable.getMessage());
                            }
                        }
                );

        compositeDisposable.add(disposable);
    }
}
