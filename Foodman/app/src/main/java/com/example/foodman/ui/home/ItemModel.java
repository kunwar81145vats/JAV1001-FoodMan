package com.example.foodman.ui.home;

public class ItemModel {

    public int id;
    public String name;
    public String description;
    public String img;

    // Constructor
    public ItemModel(String course_name, int id, String desc, String image) {
        this.id = id;
        this.name = course_name;
        this.description = desc;
        this.img = image;
    }

    public boolean equals(ItemModel item) {
        if(this.name.equals(item.id) &&
                this.id == item.id) {
            return true;
        }
        return false;
    }
}
