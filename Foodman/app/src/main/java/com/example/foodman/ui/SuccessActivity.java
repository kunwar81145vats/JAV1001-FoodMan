package com.example.foodman.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.foodman.MainActivity;
import com.example.foodman.R;
import com.example.foodman.ui.home.CommonSingleton;

public class SuccessActivity extends AppCompatActivity {

    AppCompatButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        getSupportActionBar().hide();

        CommonSingleton.shared().pastOrders.add(CommonSingleton.shared().currentOrder);
        CommonSingleton.shared().currentOrder = null;
        CommonSingleton.shared().savePastOrder();
        CommonSingleton.shared().saveCurrentOrder();

        closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( v.getContext(), MainActivity.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
            }
        });

    }

}
