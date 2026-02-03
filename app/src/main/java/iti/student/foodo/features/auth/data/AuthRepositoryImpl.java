package iti.student.foodo.features.auth.data;

import iti.student.foodo.data.remote.firebase.FirebaseAuthDataSource;

public class AuthRepositoryImpl implements AuthRepository {

    private final FirebaseAuthDataSource dataSource;

    public AuthRepositoryImpl(FirebaseAuthDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        dataSource.login(email, password, callback);
    }

    @Override
    public void register(String email, String password, AuthCallback callback) {
        dataSource.register(email, password, callback);
    }
}
