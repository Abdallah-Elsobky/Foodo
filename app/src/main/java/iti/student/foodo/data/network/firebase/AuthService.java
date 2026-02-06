package iti.student.foodo.data.network.firebase;

import android.util.Log;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import iti.student.foodo.features.auth.data.AuthRepository;

public class AuthService {

    private final FirebaseAuth firebaseAuth;

    public AuthService() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password,
                      AuthRepository.AuthCallback callback) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> {
                    if (isVerified()) {
                        callback.onSuccess();
                    } else {
                        callback.onError("Email not verified");
                    }
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void register(String email, String password,
                         AuthRepository.AuthCallback callback) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void loginWithGoogle(String idToken, AuthRepository.AuthCallback callback) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(result -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void loginWithFacebook(String accessToken, AuthRepository.AuthCallback callback) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(result -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void loginAsGuest(AuthRepository.AuthCallback callback) {
        firebaseAuth.signInAnonymously()
                .addOnSuccessListener(authResult -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void sendEmailVerification(AuthRepository.AuthCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification();
        if (user != null) {
            callback.onSuccess();
        } else {
            callback.onError("User not logged in");
        }
    }

    public boolean isVerified() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            return user.isEmailVerified();
        } else {
            return false;
        }
    }

    public void logout() {
        firebaseAuth.signOut();
    }
}

