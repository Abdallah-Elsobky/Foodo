package iti.student.foodo.features.search.presenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.repository.MealRepository;

public class SearchPresenter implements SearchContract.Presenter {

    final MealRepository repository;
    SearchContract.View view;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public SearchPresenter(MealRepository repository) {
        this.repository = repository;
    }


    @Override
    public void getMeals() {
        Disposable disposable = repository.getMeals().observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.onLoadMeals(meals),
                throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsBySearch(String query) {
        Disposable disposable = repository.searchByName(query).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.onLoadMeals(meals),
                throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsByCategory(String category) {
        Disposable disposable = repository.searchByCategory(category).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.onLoadMeals(meals),
                throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsByCountry(String country) {
        Disposable disposable = repository.searchByArea(country).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.onLoadMeals(meals),
                throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        Disposable disposable = repository.searchByIngredient(ingredient).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.onLoadMeals(meals),
                throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getCountries() {
        Disposable disposable = repository.getAreas().observeOn(AndroidSchedulers.mainThread()).subscribe(
                countries -> view.onLoadCountries(countries),
                throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getCategories() {
        Disposable disposable = repository.getCategories().observeOn(AndroidSchedulers.mainThread()).subscribe(
                countries -> view.onLoadCategories(countries),
                throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getIngredients() {
        Disposable disposable = repository.getIngredients().observeOn(AndroidSchedulers.mainThread()).subscribe(
                ingredients -> view.onLoadIngredients(ingredients),
                throwable -> view.showError(throwable.getMessage())
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
