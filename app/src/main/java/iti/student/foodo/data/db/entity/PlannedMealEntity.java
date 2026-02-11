package iti.student.foodo.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "planned_meals",
        primaryKeys = {"userId", "date", "mealId"}
)
public class PlannedMealEntity {

    @NonNull
    public String userId;

    @NonNull
    public String date;

    @NonNull
    public String mealId;

    public PlannedMealEntity() {

    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    public PlannedMealEntity(
            @NonNull String userId,
            @NonNull String date,
            @NonNull String mealId
    ) {
        this.userId = userId;
        this.date = date;
        this.mealId = mealId;
    }
}

