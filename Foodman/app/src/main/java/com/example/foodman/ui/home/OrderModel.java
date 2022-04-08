package com.example.foodman.ui.home;

import java.util.ArrayList;

public class OrderModel {

    public int id;
    public ArrayList<ItemModel> items;

    // Constructor
    public OrderModel(int id, ArrayList<ItemModel> items) {
        this.id = id;
        this.items = items;
    }

    public OrderModel(int id)
    {
        this.id = id;
    }
}
