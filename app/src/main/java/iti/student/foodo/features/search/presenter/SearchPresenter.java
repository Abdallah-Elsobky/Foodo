package iti.student.foodo.features.search.presenter;

import static iti.student.foodo.features.utils.ErrorUtils.getErrorMessage;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.repository.auth.AuthRepository;
import iti.student.foodo.data.repository.favorite.FavoriteRepository;
import iti.student.foodo.data.repository.meal.MealRepository;
import iti.student.foodo.data.repository.planner.PlannerRepository;

public class SearchPresenter implements SearchContract.Presenter {

    final MealRepository mealRepository;
    final FavoriteRepository favoriteRepository;
    final PlannerRepository plannerRepository;
    SearchContract.View view;
    private final AuthRepository authRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public SearchPresenter(MealRepository mealRepository,
                           FavoriteRepository favoriteRepository,
                           PlannerRepository plannerRepository, AuthRepository authRepository) {
        this.mealRepository = mealRepository;
        this.favoriteRepository = favoriteRepository;
        this.plannerRepository = plannerRepository;
        this.authRepository = authRepository;
    }

    @Override
    public void getLocalMeals() {
        Disposable disposable = mealRepository.getLocalMeals()
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            if (view != null) {
                                if (meals.isEmpty()) {
                                    view.showError("No cached meals available");
                                } else {
                                    view.onLoadMeals(meals);
                                }
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.showError(getErrorMessage(throwable));
                            }
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsBySearch(String query) {
        Disposable disposable = mealRepository.searchByName(query).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.onLoadMeals(meals),
                throwable -> view.showError(getErrorMessage(throwable))
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsByCategory(String category) {
        Disposable disposable = mealRepository.searchByCategory(category).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.onLoadMeals(meals),
                throwable -> view.showError(getErrorMessage(throwable))
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsByCountry(String country) {
        Disposable disposable = mealRepository.searchByArea(country).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.onLoadMeals(meals),
                throwable -> view.showError(getErrorMessage(throwable))
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        Disposable disposable = mealRepository.searchByIngredient(ingredient).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.onLoadMeals(meals),
                throwable -> view.showError(getErrorMessage(throwable))
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getCountries() {
        Disposable disposable = mealRepository.getAreas().observeOn(AndroidSchedulers.mainThread()).subscribe(
                countries -> view.onLoadCountries(countries),
                throwable -> view.showError(getErrorMessage(throwable))
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getCategories() {
        Disposable disposable = mealRepository.getCategories().observeOn(AndroidSchedulers.mainThread()).subscribe(
                countries -> view.onLoadCategories(countries),
                throwable -> view.showError(getErrorMessage(throwable))
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getIngredients() {
        Disposable disposable = mealRepository.getIngredients().observeOn(AndroidSchedulers.mainThread()).subscribe(
                ingredients -> view.onLoadIngredients(ingredients),
                throwable -> view.showError(getErrorMessage(throwable))
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void addToFavorites(String mealId) {
        if(authRepository.isGuest()){
            view.showError("You must be logged in to add a meal to your planner");
            return;
        }
        Disposable disposable = favoriteRepository.addToFavorites(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    view.showToast("Meal added to favorites");
                    mealRepository.searchById(mealId).subscribe();
                },
                throwable -> view.showError(getErrorMessage(throwable))
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteFromFavorites(String mealId) {
        if(authRepository.isGuest()){
            view.showError("You must be logged in to add a meal to your planner");
            return;
        }
        Disposable disposable = favoriteRepository.removeFromFavorites(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> view.showToast("Meal removed from favorites"),
                throwable -> view.showError(getErrorMessage(throwable))
        );
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
                },
                throwable -> view.showError(getErrorMessage(throwable))
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        compositeDisposable.clear();
    }
}
