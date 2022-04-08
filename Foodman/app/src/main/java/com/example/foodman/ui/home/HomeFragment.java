package com.example.foodman.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodman.ui.CheckoutActivity;
import com.example.foodman.HomeAdapter;
import com.example.foodman.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements HomeAdapter.CurrentOrderListener {

    private FragmentHomeBinding binding;
    AppCompatButton checkoutButton;
    HomeAdapter homeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        checkoutButton = binding. buttonDel;
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });
        CommonSingleton.getJsonFromAssets(getActivity().getApplicationContext(), "FoodItems.json");
        RecyclerView itemsRecyclerView = binding.ItemsRecyclerView;
        updateCheckoutButton();

        homeAdapter = new HomeAdapter(getContext(), CommonSingleton.foodList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        itemsRecyclerView.setLayoutManager(linearLayoutManager);
        itemsRecyclerView.setAdapter(homeAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCheckoutButton();
        homeAdapter.notifyDataSetChanged();
    }


    public void updateCheckoutButton()
    {
        if (CommonSingleton.currentOrder == null)
        {
            checkoutButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            if(CommonSingleton.currentOrder.items.size() == 0)
            {
                checkoutButton.setVisibility(View.INVISIBLE);
            }
            else
            {
                checkoutButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void currentOrderUpdated() {
        updateCheckoutButton();
    }
}