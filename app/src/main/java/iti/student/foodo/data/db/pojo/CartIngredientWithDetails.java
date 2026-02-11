package iti.student.foodo.data.db.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import iti.student.foodo.data.db.entity.CartIngredientEntity;
import iti.student.foodo.data.db.entity.IngredientEntity;

public class CartIngredientWithDetails {

    @Embedded
    public CartIngredientEntity cart;

    @Relation(
            parentColumn = "ingredientName",
            entityColumn = "name"
    )
    public IngredientEntity ingredient;
}
