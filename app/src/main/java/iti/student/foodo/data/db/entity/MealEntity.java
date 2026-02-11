package iti.student.foodo.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "meals")
public class MealEntity {

    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String image;
    public String category;
    public String area;
    public String youtube;

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getYoutube() {
        return youtube;
    }

    public MealEntity(@NonNull String id, String name, String image, String category, String area, String youtube) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.area = area;
        this.youtube = youtube;
    }
}


