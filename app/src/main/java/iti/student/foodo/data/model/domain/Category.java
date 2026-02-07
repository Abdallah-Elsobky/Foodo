package iti.student.foodo.data.model.domain;

import static com.google.android.gms.common.util.CollectionUtils.listOf;

import java.util.List;

public class Category {
    private String name;
    private String image;
    private String imageIcon;
    private String description;

    public Category(String name, String image, String description, String imageIcon) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.imageIcon = imageIcon;
    }

    public String getName() {
        return name;
    }

    public String getImageIcon() {
        return imageIcon;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public static List<Category> homeCategoryList() {
        return List.of(
                new Category(
                        "Beef",
                        "https://www.themealdb.com/images/category/beef.png",
                        "Beef is the culinary name for meat from cattle, particularly skeletal muscle.",
                        "🥩"
                ),
                new Category(
                        "Chicken",
                        "https://www.themealdb.com/images/category/chicken.png",
                        "Chicken is a type of domesticated fowl and one of the most common food sources worldwide.",
                        "🍗"
                ),
                new Category(
                        "Dessert",
                        "https://www.themealdb.com/images/category/dessert.png",
                        "Dessert is a course that concludes a meal, usually consisting of sweet foods.",
                        "🍰"
                ),
                new Category(
                        "Lamb",
                        "https://www.themealdb.com/images/category/lamb.png",
                        "Lamb, hogget, and mutton are the meat of domestic sheep at different ages.",
                        "🐑"
                ),
                new Category(
                        "Miscellaneous",
                        "https://www.themealdb.com/images/category/miscellaneous.png",
                        "General foods that don't fit into another category.",
                        "🥙"
                ),
                new Category(
                        "Pasta",
                        "https://www.themealdb.com/images/category/pasta.png",
                        "Pasta is a staple food of traditional Italian cuisine.",
                        "🍝"
                ),
                new Category(
                        "Pork",
                        "https://www.themealdb.com/images/category/pork.png",
                        "Pork is the culinary name for meat from a domestic pig.",
                        "🐖"
                ),
                new Category(
                        "Seafood",
                        "https://www.themealdb.com/images/category/seafood.png",
                        "Seafood is any form of sea life regarded as food by humans.",
                        "🦞"
                ),
                new Category(
                        "Side",
                        "https://www.themealdb.com/images/category/side.png",
                        "A side dish accompanies the main course of a meal.",
                        "🥗"
                ),
                new Category(
                        "Starter",
                        "https://www.themealdb.com/images/category/starter.png",
                        "A dish served before the main course of a meal.",
                        "🥣"
                ),
                new Category(
                        "Vegan",
                        "https://www.themealdb.com/images/category/vegan.png",
                        "Veganism excludes all animal products from the diet.",
                        "🍅"
                ),
                new Category(
                        "Vegetarian",
                        "https://www.themealdb.com/images/category/vegetarian.png",
                        "Vegetarianism abstains from consuming meat.",
                        "🥦"
                ),
                new Category(
                        "Breakfast",
                        "https://www.themealdb.com/images/category/breakfast.png",
                        "Breakfast is the first meal of the day.",
                        "🍳"
                ),
                new Category(
                        "Goat",
                        "https://www.themealdb.com/images/category/goat.png",
                        "Goat meat and milk are used widely across the world.",
                        "🍖"
                )
        );

    }
}
