package iti.student.foodo.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(
        tableName = "favorite_meals",
        primaryKeys = {"userId", "mealId"},
        indices = {@Index("mealId")}
)
public class FavoriteMealEntity {
    @NonNull
    public String userId;
    @NonNull
    public String mealId;

    public long addedAt;

    public FavoriteMealEntity(String userId, String mealId, long addedAt) {
        this.userId = userId;
        this.mealId = mealId;
        this.addedAt = addedAt;
    }
}
