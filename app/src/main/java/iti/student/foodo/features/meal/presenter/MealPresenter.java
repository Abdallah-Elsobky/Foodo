package iti.student.foodo.features.meal.presenter;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.mapper.IngredientMapper;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.network.firebase.AuthService;
import iti.student.foodo.data.repository.auth.AuthRepository;
import iti.student.foodo.data.repository.auth.AuthRepositoryImpl;
import iti.student.foodo.data.repository.cart.CartRepository;
import iti.student.foodo.data.repository.favorite.FavoriteRepository;
import iti.student.foodo.data.repository.meal.MealRepository;
import iti.student.foodo.data.repository.planner.PlannerRepository;

public class MealPresenter implements MealContract.Presenter {

    MealContract.View view;
    final MealRepository mealRepository;
    final FavoriteRepository favoriteRepository;
    final CartRepository cartRepository;
    final AuthRepository authRepository;
    final PlannerRepository plannerRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public MealPresenter(MealRepository mealRepository,
                         FavoriteRepository favoriteRepository,
                         CartRepository cartRepository, AuthRepository authRepository, PlannerRepository plannerRepository) {
        this.mealRepository = mealRepository;
        this.favoriteRepository = favoriteRepository;
        this.cartRepository = cartRepository;
        this.authRepository = authRepository;
        this.plannerRepository = plannerRepository;
    }

    @Override
    public void getMeals(String id) {
        Disposable disposable = mealRepository.searchById(id).observeOn(AndroidSchedulers.mainThread()).subscribe(
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
        if (authRepository.isGuest()) {
            view.showError("You must be logged in to add a meal to your favorites");
            return;
        }
        Disposable disposable = favoriteRepository.addToFavorites(mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    view.showToast("Meal added to favorites");
                }
                , throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void removeFromFavorites(String userId, String mealId) {
        if (authRepository.isGuest()) {
            view.showError("You must be logged in to add a meal to your planner");
            return;
        }
    }

    @Override
    public void isFavorite(String userId, String mealId) {

    }

    @Override
    public void addToPlanner(String date, String mealId) {
        if (authRepository.isGuest()) {
            view.showError("You must be logged in to add a meal to your planner");
            return;
        }
        Disposable disposable = plannerRepository.addPlannedMeal(date, mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    view.showToast("Meal added to planner");
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void addIngredientToCart(Ingredient ingredient) {
        if (authRepository.isGuest()) {
            view.showError("You must be logged in to add a meal to your planner");
            return;
        }
        Disposable disposable = cartRepository.addToCart(IngredientMapper.toCartEntity(ingredient)).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    view.showToast("Ingredient added to cart");
                }
                , throwable -> view.showError(throwable.getMessage())
        );
        compositeDisposable.add(disposable);
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
