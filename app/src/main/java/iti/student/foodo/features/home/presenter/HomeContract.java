package iti.student.foodo.features.home.presenter;


import java.util.List;

import iti.student.foodo.core.base.BasePresenter;
import iti.student.foodo.core.base.BaseView;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Meal;

public interface HomeContract {
    interface View extends BaseView {
        void showMeals(List<Meal> meals);

        void showRandomMeal(Meal meal);

        void showToast(String message);

        void onLogout();

        void addNetworkListener();

        void removeNetworkListener();
    }

    interface Presenter extends BasePresenter<View> {
        void getMeals();

        void getRandomMeal();

        void addFavorite(String mealId);

        void removeFavorite(String mealId);

        void addMealToPlanner(String date, String mealId);

        void logout();
    }
}
