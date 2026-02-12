package iti.student.foodo.features.meal.presenter;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.mapper.IngredientMapper;
import iti.student.foodo.data.model.domain.Ingredient;
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
                , throwable -> Log.d("loco", "error: " + throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void addToFavorites(String mealId) {
        repository.addToFavorites(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    Log.d("loco", "meal added: " + mealId);
                }
                , throwable -> Log.d("loco", "error: " + throwable.getMessage())
        );
    }

    @Override
    public void removeFromFavorites(String userId, String mealId) {

    }

    @Override
    public void isFavorite(String userId, String mealId) {

    }

    @Override
    public void addIngredientToCart(Ingredient ingredient) {
        repository.addTOCart(IngredientMapper.toCartEntity(ingredient)).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @Override
    public void removeIngredientFromCart(Ingredient ingredient) {

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
