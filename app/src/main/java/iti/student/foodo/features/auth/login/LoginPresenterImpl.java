package iti.student.foodo.features.auth.login;

import iti.student.foodo.features.auth.data.AuthRepository;

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View view;
    private AuthRepository repository;

    public LoginPresenterImpl(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public void login(String email, String password) {
        view.showLoading();
        repository.login(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                view.hideLoading();
                view.onLoginSuccess();
            }

            @Override
            public void onError(String message) {
                view.onLoginFailure(message);
                view.hideLoading();
                view.showError(message);
            }
        });
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
