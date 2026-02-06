package iti.student.foodo.features.auth.login;

import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialResponse;

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

import iti.student.foodo.features.auth.data.AuthRepository;

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View view;
    private final AuthRepository repository;

    private AuthRepository.AuthCallback loginCallback = new AuthRepository.AuthCallback() {
        @Override
        public void onSuccess() {
            view.hideLoading();
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

    public LoginPresenterImpl(AuthRepository repository) {
        this.repository = repository;
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
