package iti.student.foodo.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "cart",
        primaryKeys = {"ingredientName", "measure"}
)
public class CartIngredientEntity {

    @NonNull
    public String ingredientName;

    @NonNull
    public String measure;

    public String imageUrl;

    public CartIngredientEntity(
            @NonNull String ingredientName,
            @NonNull String measure,
            String imageUrl
    ) {
        this.ingredientName = ingredientName;
        this.measure = measure;
        this.imageUrl = imageUrl;
    }
}

