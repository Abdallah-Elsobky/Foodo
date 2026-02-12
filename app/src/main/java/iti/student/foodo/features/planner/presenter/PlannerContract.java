package iti.student.foodo.features.planner.presenter;

import java.util.List;

import iti.student.foodo.core.base.BasePresenter;
import iti.student.foodo.core.base.BaseView;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;
import iti.student.foodo.data.model.domain.Meal;

public interface PlannerContract {
    interface View extends BaseView {
        void showMeals(List<PlannedMealWithDetails> meals);
    }

    interface Presenter extends BasePresenter<View>{
        void getMeals(String date);

        void removeMeal(String date, String mealId);
    }
}
