package iti.student.foodo.features.favorites.presenter;

import java.util.List;

import iti.student.foodo.core.base.BasePresenter;
import iti.student.foodo.core.base.BaseView;
import iti.student.foodo.data.model.domain.Meal;

public interface FavContract {
    public interface View extends BaseView {
        void showFavorites(List<Meal> meals);
    }

    public interface Presenter extends BasePresenter<View> {
        void getFavorites();
        void removeFavorite(String mealId);
    }

}
