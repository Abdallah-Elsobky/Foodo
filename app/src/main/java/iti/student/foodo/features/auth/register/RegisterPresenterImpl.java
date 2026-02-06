package iti.student.foodo.features.auth.register;

import iti.student.foodo.features.auth.data.AuthRepository;

public class RegisterPresenterImpl implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private final AuthRepository repository;

    public RegisterPresenterImpl(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public void register(String email, String password) {
        view.showLoading();
        repository.register(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                repository.sendEmailVerification(new AuthRepository.AuthCallback() {
                    @Override
                    public void onSuccess() {
                        view.hideLoading();
                        view.onRegisterSuccess();
                    }

                    @Override
                    public void onError(String message) {
                        view.showError(message);
                    }
                });
            }

            @Override
            public void onError(String message) {
                view.hideLoading();
                view.showError(message);
                view.onRegisterFailure(message);
            }
        });
    }

    @Override
    public void attachView(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
