package iti.student.foodo.core.session;

public interface UserSessionRepository {
    void saveToken(String token);

    String getToken();

    void saveUserId(int id);

    int getUserId();

    boolean isLoggedIn();

    void logout();
}
