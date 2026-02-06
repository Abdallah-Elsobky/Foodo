package iti.student.foodo.data.model;

public class MainItem {
    private String name;
    private String image;
    private String id;

    public MainItem(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
