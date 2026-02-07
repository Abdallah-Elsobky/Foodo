package iti.student.foodo.data.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.domain.Instruction;
import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.model.dto.CategoriesItem;
import iti.student.foodo.data.model.dto.CountriesItem;
import iti.student.foodo.data.model.dto.MealsItem;

public class Mapper {


    // Meal Mapper

    public static Meal map(MealsItem item) {
        Meal meal = new Meal();

        meal.setName(item.getStrMeal());
        meal.setImage(item.getStrMealThumb());
        meal.setCategory(item.getStrCategory());
        meal.setArea(item.getStrArea());
        meal.setYoutube(item.getStrYoutube());

        meal.setIngredients(mapIngredients(item));
        meal.setInstructions(mapInstructions(item.getStrInstructions()));

        return meal;
    }

    public static List<Meal> mapList(List<MealsItem> items) {
        List<Meal> meals = new ArrayList<>();
        if (items == null) return meals;

        for (MealsItem item : items) {
            meals.add(map(item));
        }
        return meals;
    }


    // Category Mapper

    public static Category map(CategoriesItem item) {
        return new Category(item.getStrCategory(), item.getStrCategoryThumb(), item.getStrCategoryDescription(), "🍽️");
    }

    public static List<Category> mapCategories(List<CategoriesItem> items) {
        List<Category> categories = new ArrayList<>();
        if (items == null) return categories;
        for (CategoriesItem item : items) {
            categories.add(map(item));
        }
        return categories;
    }


    // Country Mapper

    public static Country map(CountriesItem item) {
        return new Country(item.getStrArea());
    }

    public static List<Country> mapCountries(List<CountriesItem> items) {
        List<Country> countries = new ArrayList<>();
        if (items == null) return countries;
        for (CountriesItem item : items) {
            countries.add(map(item));
        }
        return countries;
    }

    // Ingredient Mapper

    private static List<Ingredient> mapIngredients(MealsItem item) {
        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            try {
                Field ingredientField =
                        MealsItem.class.getDeclaredField("strIngredient" + i);
                Field measureField =
                        MealsItem.class.getDeclaredField("strMeasure" + i);

                ingredientField.setAccessible(true);
                measureField.setAccessible(true);

                String ingredient = (String) ingredientField.get(item);
                String measure = (String) measureField.get(item);
                if (ingredient != null && !ingredient.trim().isEmpty()) {
                    String image = "https://www.themealdb.com/images/ingredients/" + ingredient.toLowerCase() + ".png";
                    ingredients.add(
                            new Ingredient(ingredient, measure, image)
                    );
                }

            } catch (Exception ignored) {
            }
        }

        return ingredients;
    }


    // Instruction Mapper

    private static List<Instruction> mapInstructions(String instructionsText) {
        List<Instruction> instructions = new ArrayList<>();

        if (instructionsText == null) return instructions;

        String[] steps = instructionsText.split("\\.");

        for (String step : steps) {
            if (!step.trim().isEmpty()) {
                instructions.add(new Instruction(step.trim()));
            }
        }
        return instructions;
    }
}

