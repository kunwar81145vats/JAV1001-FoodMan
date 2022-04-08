package com.example.foodman.ui.home;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CommonSingleton {

    public static ArrayList<ItemModel> foodList;
    public static OrderModel currentOrder;
    public static ArrayList<OrderModel> pastOrders = new ArrayList();
    public static ArrayList<ItemModel> favItems = new ArrayList();

    private static final CommonSingleton ourInstance = new CommonSingleton();

    public static CommonSingleton getInstance() {
        return ourInstance;
    }

    private CommonSingleton() {
    }

    static void getJsonFromAssets(Context context, String fileName) {
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
    }
}
