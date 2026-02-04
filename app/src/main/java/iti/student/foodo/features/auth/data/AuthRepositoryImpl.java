package iti.student.foodo.features.auth.data;

import iti.student.foodo.data.remote.firebase.AuthService;

public class AuthRepositoryImpl implements AuthRepository {

    private final AuthService authService;

    public AuthRepositoryImpl(AuthService dataSource) {
        this.authService = dataSource;
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        authService.login(email, password, callback);
    }

    @Override
    public void register(String email, String password, AuthCallback callback) {
        authService.register(email, password, callback);
    }

    public void loginWithGoogle(String idToken, AuthCallback callback) {
        authService.loginWithGoogle(idToken, callback);
    }

    public void loginWithFacebook(String accessToken, AuthCallback callback) {
        authService.loginWithFacebook(accessToken, callback);
    }

    public void logout(AuthCallback callback) {
        authService.logout();
    }
}
