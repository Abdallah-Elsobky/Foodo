package iti.student.foodo.data.db.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "ingredients",
        indices = {@Index(value = {"mealId", "name", "measure"}, unique = true)}
)
public class IngredientEntity {

    @PrimaryKey(autoGenerate = true)
    public int ingredientId;

    @NonNull
    public String mealId;

    @NonNull
    public String name;

    @NonNull
    public String measure;

    public String imageUrl;

    public IngredientEntity(@NonNull String mealId, @NonNull String name, @NonNull String measure, String imageUrl) {
        this.mealId = mealId;
        this.name = name;
        this.measure = measure;
        this.imageUrl = imageUrl;
    }
}

