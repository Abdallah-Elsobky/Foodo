package iti.student.foodo.features.search.presenter;

import java.util.List;

import iti.student.foodo.core.base.BasePresenter;
import iti.student.foodo.core.base.BaseView;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.domain.Meal;

public interface SearchContract {

    interface View extends BaseView {
        void onLoadMeals(List<Meal> meals);

        void onLoadCountries(List<Country> countries);

        void onLoadIngredients(List<Ingredient> ingredients);

        void onLoadCategories(List<Category> categories);
    }

    interface Presenter extends BasePresenter<View> {
        void getMeals();

        void getMealsBySearch(String query);

        void getMealsByCategory(String category);

        void getMealsByCountry(String country);

        void getMealsByIngredient(String ingredient);

        void getCountries();

        void getCategories();

        void getIngredients();
    }
}
