package iti.student.foodo.features.meal.presenter;

import iti.student.foodo.core.base.BasePresenter;
import iti.student.foodo.core.base.BaseView;
import iti.student.foodo.data.model.domain.Meal;

public interface MealContract {
    interface View extends BaseView {
        void onLoadMeals(Meal meal);
    }

    interface Presenter extends BasePresenter<View> {
        void getMeals(String id);
    }
}
