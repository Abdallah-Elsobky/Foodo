package iti.student.foodo.features.auth.login.presenter;

import android.util.Log;

import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialResponse;

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.datasource.local.PrefManager;
import iti.student.foodo.data.db.entity.PlannedMealEntity;
import iti.student.foodo.data.repository.auth.AuthRepository;
import iti.student.foodo.data.repository.favorite.FavoriteRepository;
import iti.student.foodo.data.repository.meal.MealRepository;
import iti.student.foodo.data.repository.planner.PlannerRepository;

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View view;
    private final AuthRepository repository;
    private final PrefManager prefManager;
    private final FavoriteRepository favoriteRepository;
    private final PlannerRepository plannerRepository;
    private final MealRepository mealRepository;

    private final AuthRepository.AuthCallback loginCallback = new AuthRepository.AuthCallback() {
        @Override
        public void onSuccess() {
            view.hideLoading();
            Log.d("loco", "before cloud: ");
            Disposable disposable1 = favoriteRepository.getFavoritesFromCloud().subscribe(
                    mealIds -> {
                        Log.d("loco", "mealIds: " + mealIds);
                        for (String mealId : mealIds) {
                            Log.d("loco", "mealId: " + mealId);
                            mealRepository.searchById(mealId).subscribe();
                            favoriteRepository.addToFavorites(mealId).subscribe();
                        }
                    }
            );
            Log.d("loco", "after cloud: ");
            Disposable disposable2 =plannerRepository.getPlannedMealsFromCloud().subscribe(
                    meals -> {
                        Log.d("loco", "planned: " + meals);
                        for (PlannedMealEntity meal : meals) {
                            Log.d("loco", "planned: " + meal);
                            mealRepository.searchById(meal.mealId).subscribe();
                            plannerRepository.addPlannedMeal(meal.date,meal.mealId).subscribe();
                        }
                    }
            );
            prefManager.setFirstTime(false);
            view.onLoginSuccess();
            view.enableButtons();
        }

        @Override
        public void onError(String message) {
            view.onLoginFailure(message);
            view.hideLoading();
            view.showError(message);
            view.enableButtons();
        }
    };

    private AuthRepository.AuthCallback logoutCallback = new AuthRepository.AuthCallback() {

        @Override
        public void onSuccess() {
            view.hideLoading();
            view.enableButtons();
        }

        @Override
        public void onError(String message) {
            view.hideLoading();
            view.showError(message);
            view.enableButtons();
        }
    };

    public LoginPresenterImpl(AuthRepository repository, PrefManager prefManager, FavoriteRepository favoriteRepository, PlannerRepository plannerRepository, MealRepository mealRepository) {
        this.repository = repository;
        this.prefManager = prefManager;
        this.favoriteRepository = favoriteRepository;
        this.plannerRepository = plannerRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    public void login(String email, String password) {
        view.showLoading();
        view.disableButtons();
        repository.login(email, password, loginCallback);
    }

    @Override
    public void loginWithGoogle(GetCredentialResponse response) {

        if (!(response.getCredential() instanceof CustomCredential)) {
            view.showError("Invalid Google credential");
            return;
        }

        CustomCredential credential = (CustomCredential) response.getCredential();

        if (!GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(credential.getType())) {
            view.showError("Unexpected credential type");
            return;
        }

        GoogleIdTokenCredential googleCredential =
                GoogleIdTokenCredential.createFrom(credential.getData());
        view.showLoading();
        view.disableButtons();
        repository.loginWithGoogle(
                googleCredential.getIdToken(),
                loginCallback
        );

    }

    @Override
    public void onGoogleSignInClicked() {
        view.launchGoogleSignIn();
    }

    @Override
    public void loginAsGuest() {
        view.showLoading();
        view.disableButtons();
        repository.loginAsGuest(loginCallback);
    }


    @Override
    public void loginWithFacebook(String accessToken) {

    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
