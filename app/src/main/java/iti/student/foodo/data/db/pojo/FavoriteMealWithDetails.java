package iti.student.foodo.data.db.pojo;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import iti.student.foodo.data.db.entity.IngredientEntity;
import iti.student.foodo.data.db.entity.InstructionEntity;
import iti.student.foodo.data.db.entity.MealEntity;

public class FavoriteMealWithDetails {

    @Embedded
    public MealEntity meal;

    @Relation(
            parentColumn = "id",
            entityColumn = "mealId"
    )
    public List<InstructionEntity> instructions;

    @Relation(
            parentColumn = "id",
            entityColumn = "mealId"
    )
    public List<IngredientEntity> ingredients;
}
