package iti.student.foodo.data.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import iti.student.foodo.data.db.entity.IngredientEntity;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.dto.IngredientsItem;
import iti.student.foodo.data.model.dto.MealsItem;

public class IngredientMapper {

    public static Ingredient map(IngredientsItem item) {
        return new Ingredient("", item.getStrIngredient(), "", item.getStrThumb());
    }

    public static List<Ingredient> mapList(List<IngredientsItem> items) {
        List<Ingredient> ingredients = new ArrayList<>();
        if (items == null) return ingredients;

        for (IngredientsItem item : items) {
            ingredients.add(map(item));
        }
        return ingredients;
    }

    static List<Ingredient> mapIngredients(MealsItem item) {
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
                    String image =
                            "https://www.themealdb.com/images/ingredients/"
                                    + ingredient.toLowerCase() + ".png";

                    ingredients.add(
                            new Ingredient(
                                    item.getIdMeal(),
                                    ingredient,
                                    measure,
                                    image
                            )
                    );
                }
            } catch (Exception ignored) {}
        }

        return ingredients;
    }

    public static IngredientEntity toEntity(Ingredient ingredient) {
        return new IngredientEntity(
                ingredient.getMealId(),
                ingredient.getName(),
                ingredient.getMeasure(),
                ingredient.getImage()
        );
    }

    public static List<IngredientEntity> toEntityList(List<Ingredient> ingredients) {
        List<IngredientEntity> entities = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            entities.add(toEntity(ingredient));
        }
        return entities;
    }



    // Database mapper
    public static Ingredient fromEntity(IngredientEntity entity) {
        return new Ingredient(
                entity.getMealId(),
                entity.getName(),
                entity.getMeasure(),
                entity.getImageUrl()
        );
    }

    public static List<Ingredient> fromEntityList(List<IngredientEntity> entities) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientEntity entity : entities) {
            ingredients.add(fromEntity(entity));
        }
        return ingredients;
    }
}

