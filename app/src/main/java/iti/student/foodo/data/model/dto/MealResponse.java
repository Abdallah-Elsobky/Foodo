package iti.student.foodo.data.model.dto;

import java.util.List;

import iti.student.foodo.data.model.domain.Meal;

public class MealResponse {
    private List<MealsItem> meals;

    public List<MealsItem> getMeals() {
        return meals;
    }

    public List<Meal> toMeals() {
        List<Meal> mealso = List.of();
        for (MealsItem m : meals) {
            Meal meal = new Meal();
            meal.setName(m.getStrMeal());
            meal.setImage(m.getStrMealThumb());
            meal.setArea(m.getStrArea());
            meal.setCategory(m.getStrCategory());
            meal.setYoutube(m.getStrYoutube());
            mealso.add(meal);
        }
        return mealso;
    }
}
