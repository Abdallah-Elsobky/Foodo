package iti.student.foodo.data.db.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import iti.student.foodo.data.db.entity.MealEntity;
import iti.student.foodo.data.db.entity.PlannedMealEntity;

public class PlannedMealWithDetails {

    @Embedded
    public PlannedMealEntity plan;

    @Relation(
            parentColumn = "mealId",
            entityColumn = "id"
    )
    public MealEntity meal;

    public PlannedMealEntity getPlan() {
        return plan;
    }

    public MealEntity getMeal() {
        return meal;
    }
}

