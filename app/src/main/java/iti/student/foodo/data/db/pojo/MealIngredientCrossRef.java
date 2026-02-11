package iti.student.foodo.data.db.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "meal_ingredient",
        primaryKeys = {"mealId", "ingredientId"}
)
public class MealIngredientCrossRef {
    @NonNull
    public String mealId;
    @NonNull
    public int ingredientId;

    public MealIngredientCrossRef(@NonNull String mealId, @NonNull int ingredientId) {
        this.mealId = mealId;
        this.ingredientId = ingredientId;
    }
}

