package com.example.manigutmadayit;

public class item {
    String name;
    String description;
    String location;
    int image;



    public item(String name, String description, String location, int image) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.image = image;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
