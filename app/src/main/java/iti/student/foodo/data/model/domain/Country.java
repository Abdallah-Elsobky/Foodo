package iti.student.foodo.data.model.domain;

public class Country{
    private String name;
    private String image;

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Country(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
