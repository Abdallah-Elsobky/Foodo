package iti.student.foodo.data.model.domain;

public class Ingredient {
    private String name;
    private String measure;
    private String image;

    public String getImage() {
        return image;
    }

    public Ingredient(String name, String measure, String image) {
        this.name = name;
        this.measure = measure;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", measure='" + measure + '\'' +
                ", image='" + image + '\'' +
                '}' + "\n";
    }
}
