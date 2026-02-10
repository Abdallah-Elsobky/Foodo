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

    public MealEntity(@NonNull String id, String name, String image, String category, String area, String youtube) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.area = area;
        this.youtube = youtube;
    }
}


