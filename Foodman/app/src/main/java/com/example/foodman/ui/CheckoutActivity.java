package com.example.foodman.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodman.R;
import com.example.foodman.ui.CheckoutAdapter;
import com.example.foodman.ui.home.CommonSingleton;
import com.example.foodman.ui.SuccessActivity;

public class CheckoutActivity extends AppCompatActivity implements CheckoutAdapter.CheckoutListener {

    AppCompatButton placeOrderButton;
    private RecyclerView itemsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        placeOrderButton = findViewById(R.id.buttonPlaceOrder);
        itemsRecyclerView = findViewById(R.id.ItemsRecyclerView);

        CheckoutAdapter adapter = new CheckoutAdapter(this, CommonSingleton.currentOrder.items, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        itemsRecyclerView.setLayoutManager(linearLayoutManager);
        itemsRecyclerView.setAdapter(adapter);

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CommonSingleton.currentOrder.items.size() == 0)
                {
                    finish();
                }
                else
                {
                    Intent intent = new Intent(v.getContext(), SuccessActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void currentOrderUpdated() {

        placeOrderButton.setText("Go Back To Add Items");
    }
}