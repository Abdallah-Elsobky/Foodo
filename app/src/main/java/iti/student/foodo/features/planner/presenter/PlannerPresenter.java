package iti.student.foodo.features.planner.presenter;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.repository.planner.PlannerRepository;

public class PlannerPresenter implements PlannerContract.Presenter {

    PlannerContract.View view;
    final PlannerRepository plannerRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public PlannerPresenter(PlannerRepository plannerRepository) {
        this.plannerRepository = plannerRepository;
    }

    @Override
    public void getMeals(String date) {
        Disposable disposable = plannerRepository.getPlannedMeals(date).observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> view.showMeals(meals),
                throwable -> view.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void removeMeal(String date, String mealId) {
        Disposable disposable = plannerRepository.removePlannedMeal(date, mealId).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> Log.d("loco", "meal removed"),
                throwable -> view.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(PlannerContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
        view = null;
    }
}
