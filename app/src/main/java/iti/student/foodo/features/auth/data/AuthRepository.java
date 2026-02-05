package iti.student.foodo.features.auth.data;

public interface AuthRepository {
    void login(String email, String password, AuthCallback callback);

    void loginWithGoogle(String idToken, AuthCallback callback);

    void loginWithFacebook(String accessToken, AuthCallback callback);

    void logout(AuthCallback callback);

    void register(String email, String password, AuthCallback callback);

    void loginAsGuest(AuthCallback callback);


    interface AuthCallback {
        void onSuccess();

        void onError(String message);
    }
}
