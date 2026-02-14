package iti.student.foodo.features.meal.presenter;

import iti.student.foodo.core.base.BasePresenter;
import iti.student.foodo.core.base.BaseView;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.domain.Meal;

public interface MealContract {
    interface View extends BaseView {
        void onLoadMeals(Meal meal);

        void showToast(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void getMeals(String id);

        void addToFavorites(String mealId);

        void removeFromFavorites(String userId, String mealId);

        void isFavorite(String userId, String mealId);

        void addIngredientToCart(Ingredient ingredient);

        void removeIngredientFromCart(Ingredient ingredient);
    }
}
