package iti.student.foodo.data.remote.firebase;

import com.google.firebase.auth.FirebaseAuth;

import iti.student.foodo.features.auth.data.AuthRepository;

public class FirebaseAuthDataSource {

    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password,
                      AuthRepository.AuthCallback callback) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void register(String email, String password,
                         AuthRepository.AuthCallback callback) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }
}

