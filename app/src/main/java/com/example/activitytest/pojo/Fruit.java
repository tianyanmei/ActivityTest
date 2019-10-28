package com.example.activitytest.pojo;

public class Fruit {
    private  String name;
    private  int imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public Fruit() {
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", imageId=" + imageId +
                '}';
    }
}
