package iti.student.foodo.data.network.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import iti.student.foodo.data.db.entity.PlannedMealEntity;

public class FirestoreService {

    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    public FirestoreService() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    private String getUserId() {
        if (auth.getCurrentUser() != null) {
            return auth.getCurrentUser().getUid();
        }
        return null;
    }

    // =========================
    // FAVORITES
    // =========================

    public Completable addFavorite(String mealId) {
        return Completable.create(emitter -> {

            String userId = getUserId();
            if (userId == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            db.collection("users")
                    .document(userId)
                    .collection("favorites")
                    .document(mealId)
                    .set(new HashMap<>())
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable removeFavorite(String mealId) {
        return Completable.create(emitter -> {

            String userId = getUserId();
            if (userId == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            db.collection("users")
                    .document(userId)
                    .collection("favorites")
                    .document(mealId)
                    .delete()
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<List<String>> getFavorites() {
        return Single.create(emitter -> {

            String userId = getUserId();
            if (userId == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            db.collection("users")
                    .document(userId)
                    .collection("favorites")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        List<String> favoriteIds = new ArrayList<>();

                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            favoriteIds.add(doc.getId());
                        }

                        emitter.onSuccess(favoriteIds);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    // =========================
    // PLANNED MEALS
    // =========================

    public Completable addPlannedMeal(String mealId, String date) {
        return Completable.create(emitter -> {

            String userId = getUserId();
            if (userId == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("mealId", mealId);
            data.put("date", date);

            db.collection("users")
                    .document(userId)
                    .collection("plannedMeals")
                    .add(data)
                    .addOnSuccessListener(doc -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Single<List<PlannedMealEntity>> getPlannedMeals() {
        return Single.create(emitter -> {

            String userId = getUserId();
            if (userId == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            db.collection("users")
                    .document(userId)
                    .collection("plannedMeals")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        List<PlannedMealEntity> list = new ArrayList<>();

                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            PlannedMealEntity meal =
                                    doc.toObject(PlannedMealEntity.class);
                            list.add(meal);
                        }

                        emitter.onSuccess(list);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable removePlannedMeal(String mealId, String date) {
        return Completable.create(emitter -> {

            String userId = getUserId();
            if (userId == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            db.collection("users")
                    .document(userId)
                    .collection("plannedMeals")
                    .whereEqualTo("mealId", mealId)
                    .whereEqualTo("date", date)
                    .get()
                    .addOnSuccessListener(querySnapshot -> {

                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            doc.getReference().delete();
                        }

                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }


}

