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

    public boolean isBought;

    public CartIngredientEntity(
            @NonNull String ingredientName,
            @NonNull String measure,
            boolean isBought
    ) {
        this.ingredientName = ingredientName;
        this.measure = measure;
        this.isBought = isBought;
    }
}

