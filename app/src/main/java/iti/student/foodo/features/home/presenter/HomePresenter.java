package iti.student.foodo.features.home.presenter;


import static iti.student.foodo.features.utils.ErrorUtils.*;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.repository.auth.AuthRepository;
import iti.student.foodo.data.repository.favorite.FavoriteRepository;
import iti.student.foodo.data.repository.meal.MealRepository;
import iti.student.foodo.data.repository.planner.PlannerRepository;

public class HomePresenter implements HomeContract.Presenter {
    HomeContract.View view;
    final MealRepository mealRepository;
    final FavoriteRepository favoriteRepository;
    final PlannerRepository plannerRepository;
    final AuthRepository authRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomePresenter(MealRepository mealRepository,
                         FavoriteRepository favoriteRepository,
                         PlannerRepository plannerRepository,
                         AuthRepository authRepository) {
        this.mealRepository = mealRepository;
        this.favoriteRepository = favoriteRepository;
        this.plannerRepository = plannerRepository;
        this.authRepository = authRepository;
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
        Disposable disposable = mealRepository.getMeals()
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
        Disposable disposable = mealRepository.getRandomMeal()
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
        if(authRepository.isGuest()){
            view.showError("You must be logged in to add a meal to your planner");
            return;
        }
        Disposable disposable = favoriteRepository.addToFavorites(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    mealRepository.searchById(mealId).subscribe();
                    Log.d("loco", "meal added: " + mealId);
                }
                , throwable -> Log.d("loco", "error: " + throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void removeFavorite(String mealId) {
        if(authRepository.isGuest()){
            view.showError("You must be logged in to add a meal to your planner");
            return;
        }
        Disposable disposable = favoriteRepository.removeFromFavorites(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    Log.d("loco", "meal removed: " + mealId);
                }
                , throwable -> Log.d("loco", "error: " + throwable.getMessage())
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
                () -> {
                    view.showToast("Meal added to planner");
                    mealRepository.searchById(mealId).subscribe();
                }
                , throwable -> Log.d("loco", "error: " + throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void Logout() {
        authRepository.logout(new AuthRepository.AuthCallback(){
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
