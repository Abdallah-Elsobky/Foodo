package iti.student.foodo.data.datasource.local;


import java.util.List;

import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.data.model.domain.Meal;

public class MealMemoryDatasource {
    private static MealMemoryDatasource instance;

    private MealMemoryDatasource() {
    }

    public static MealMemoryDatasource getInstance() {
        if (instance == null)
            instance = new MealMemoryDatasource();

        return instance;
    }

    private Meal randomMeal;

    public Meal getRandomMeal() {
        return randomMeal;
    }

    public void setRandomMeal(Meal randomMeal) {
        this.randomMeal = randomMeal;
    }

    public void clearRandomMeal() {
        this.randomMeal = null;
    }

    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void clearCategories() {
        this.categories = null;
    }

    private List<Country> areas;

    public List<Country> getAreas() {
        return areas;
    }

    public void setAreas(List<Country> areas) {
        this.areas = areas;
    }

    public void clearAreas() {
        this.areas = null;
    }
}
