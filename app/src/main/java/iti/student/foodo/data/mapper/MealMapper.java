package iti.student.foodo.data.mapper;

import java.util.ArrayList;
import java.util.List;

import iti.student.foodo.data.db.entity.MealEntity;
import iti.student.foodo.data.db.pojo.MealWithDetails;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;
import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.data.model.domain.Instruction;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.data.model.dto.MealsItem;

public class MealMapper {


    // API mapper
    public static Meal map(MealsItem item) {
        Meal meal = new Meal();
        if(item == null)
            return meal;
        meal.setId(item.getIdMeal());
        meal.setName(item.getStrMeal());
        meal.setImage(item.getStrMealThumb());
        meal.setCategory(item.getStrCategory());
        meal.setArea(item.getStrArea());
        meal.setYoutube(item.getStrYoutube());

        meal.setIngredients(IngredientMapper.mapIngredients(item));
        meal.setInstructions(
                InstructionMapper.mapInstructions(
                        item.getStrInstructions(),
                        item.getIdMeal()
                )
        );

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


    // Database mapper
    public static MealEntity toEntity(Meal meal) {
        return new MealEntity(
                meal.getId(),
                meal.getName(),
                meal.getImage(),
                meal.getCategory(),
                meal.getArea(),
                meal.getYoutube()
        );
    }

    public static List<MealEntity> toEntityList(List<Meal> meals) {
        List<MealEntity> entities = new ArrayList<>();
        for (Meal meal : meals) {
            entities.add(toEntity(meal));
        }
        return entities;
    }


    public static Meal fromEntity(
            MealWithDetails mealWithDetails
    ) {
        Meal meal = new Meal();
        meal.setId(mealWithDetails.meal.id);
        meal.setName(mealWithDetails.meal.name);
        meal.setImage(mealWithDetails.meal.image);
        meal.setCategory(mealWithDetails.meal.category);
        meal.setArea(mealWithDetails.meal.area);
        meal.setYoutube(mealWithDetails.meal.youtube);
        meal.setIngredients(IngredientMapper.fromEntityList(mealWithDetails.ingredients));
        meal.setInstructions(InstructionMapper.fromEntityList(mealWithDetails.instructions));
        return meal;
    }

//    public static Meal fromEntity(
//            PlannedMealWithDetails plannedMealWithDetails
//    ) {
//        Meal meal = new Meal();
//        meal.setId(plannedMealWithDetails.meal.id);
//        meal.setName(plannedMealWithDetails.meal.name);
//        meal.setImage(plannedMealWithDetails.meal.image);
//        meal.setCategory(plannedMealWithDetails.meal.category);
//        meal.setArea(plannedMealWithDetails.meal.area);
//        meal.setYoutube(plannedMealWithDetails.meal.youtube);
//        return meal;
//    }

    public static Meal fromEntity(
            MealEntity mealEntity
    ) {
        Meal meal = new Meal();
        if (mealEntity == null) return meal;
        meal.setId(mealEntity.getId());
        meal.setName(mealEntity.getName());
        meal.setArea(mealEntity.getArea());
        meal.setCategory(mealEntity.getCategory());
        meal.setImage(mealEntity.getImage());
        meal.setYoutube(mealEntity.getYoutube());
        return meal;
    }


    public static List<Meal> fromEntityList(
            List<MealWithDetails> mealWithDetails
    ) {
        List<Meal> meals = new ArrayList<>();

        for (int i = 0; i < mealWithDetails.size(); i++) {
            meals.add(fromEntity(mealWithDetails.get(i)));
        }
        return meals;
    }


    public static Meal fromMealEntity(
            MealEntity mealEntity
    ) {
        Meal meal = new Meal();
        if (mealEntity == null) return meal;
        meal.setId(mealEntity.getId());
        meal.setName(mealEntity.getName());
        meal.setArea(mealEntity.getArea());
        meal.setCategory(mealEntity.getCategory());
        meal.setImage(mealEntity.getImage());
        meal.setYoutube(mealEntity.getYoutube());
        return meal;
    }

    public static List<Meal> fromMealEntityList(
            List<MealEntity> mealEntities
    ){
        List<Meal> meals = new ArrayList<>();

        for (int i = 0; i < mealEntities.size(); i++) {
            meals.add(fromEntity(mealEntities.get(i)));
        }
        return meals;
    }

}

