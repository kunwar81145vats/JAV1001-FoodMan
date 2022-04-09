package com.example.foodman.ui.home;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CommonSingleton {

    public static ArrayList<ItemModel> foodList;
    public OrderModel currentOrder;
    public ArrayList<OrderModel> pastOrders = new ArrayList();
    public ArrayList<ItemModel> favItems = new ArrayList();
    Context context;

    private static final CommonSingleton ourInstance = new CommonSingleton();

    public static CommonSingleton shared() {
        return ourInstance;
    }

    private CommonSingleton() {
    }

    //Fetch list of food items from JSON file
    public void getJsonFromAssets(Context context, String fileName) {

        this.context = context;
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<ItemModel>>() {
        }.getType();
        foodList = gson.fromJson(jsonString, listUserType);
        this.getCurrentOrder();
        this.getFavourites();
        this.getPastOrders();
    }

    //Save current order in sharedPreference
    public void saveCurrentOrder()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferenceFileName", 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(currentOrder);
        sharedPreferencesEditor.putString("current_order", serializedObject);
        sharedPreferencesEditor.apply();
    }

    //Save past orders in sharedPreference
    public void savePastOrder()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferenceFileName", 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(pastOrders);
        sharedPreferencesEditor.putString("past_order", serializedObject);
        sharedPreferencesEditor.apply();
    }

    //Get current order from sharedPreference
    void getCurrentOrder()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferenceFileName", 0);
        if (sharedPreferences.contains("current_order")) {
            final Gson gson = new Gson();
            currentOrder = gson.fromJson(sharedPreferences.getString("current_order", ""), OrderModel.class);
        }
    }

    //Get current order from sharedPreference
    void getPastOrders()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferenceFileName", 0);
        if (sharedPreferences.contains("past_order")) {
            final Gson gson = new Gson();
            Type type = new TypeToken<List<OrderModel>>(){}.getType();
            pastOrders = gson.fromJson(sharedPreferences.getString("past_order", ""), type);
        }
    }

    //Get favourites from sharedPreference
    void getFavourites()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferenceFileName", 0);
        if (sharedPreferences.contains("favourite_items")) {
            final Gson gson = new Gson();
            Type type = new TypeToken<List<ItemModel>>(){}.getType();
            favItems = gson.fromJson(sharedPreferences.getString("favourite_items", ""), type);
        }
    }

    //Save favourtes in sharedPreference
    public void saveFavourites()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferenceFileName", 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(favItems);
        sharedPreferencesEditor.putString("favourite_items", serializedObject);
        sharedPreferencesEditor.apply();
    }
}
