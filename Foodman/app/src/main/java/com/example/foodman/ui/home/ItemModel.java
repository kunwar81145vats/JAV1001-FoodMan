package com.example.foodman.ui.home;

public class ItemModel {

    public int id;
    public String name;
    public String description;
    public String img;
    public int quantity = 1;

    // Constructor
    public ItemModel(String course_name, int id, String desc, String image, int quantity) {
        this.id = id;
        this.name = course_name;
        this.description = desc;
        this.img = image;
        this.quantity = quantity;
    }
}
