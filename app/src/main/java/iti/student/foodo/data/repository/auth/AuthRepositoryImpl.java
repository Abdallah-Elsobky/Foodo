package iti.student.foodo.data.repository.auth;

import iti.student.foodo.data.datasource.local.PrefManager;
import iti.student.foodo.data.network.firebase.AuthService;

public class AuthRepositoryImpl implements AuthRepository {

    private final AuthService authService;
    private PrefManager prefManager;

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

    @Override
    public void loginAsGuest(AuthCallback callback) {
        authService.loginAsGuest(callback);
    }

    @Override
    public void sendEmailVerification(AuthCallback callback) {
        authService.sendEmailVerification(callback);
    }

    @Override
    public boolean isVerified() {
        return authService.isVerified();
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
