package iti.student.foodo.data.mapper;

import java.util.ArrayList;
import java.util.List;

import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.dto.CategoriesItem;

public class CategoryMapper {

    public static Category map(CategoriesItem item) {
        return new Category(
                item.getStrCategory(),
                item.getStrCategoryThumb(),
                item.getStrCategoryDescription(),
                "🍽️"
        );
    }

    public static List<Category> mapList(List<CategoriesItem> items) {
        List<Category> categories = new ArrayList<>();
        if (items == null) return categories;

        for (CategoriesItem item : items) {
            categories.add(map(item));
        }
        return categories;
    }
}
