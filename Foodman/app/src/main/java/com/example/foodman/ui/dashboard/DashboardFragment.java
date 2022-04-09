package com.example.foodman.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodman.HomeAdapter;
import com.example.foodman.R;
import com.example.foodman.databinding.FragmentDashboardBinding;
import com.example.foodman.ui.home.CommonSingleton;

//Used to show list of favourite items
public class DashboardFragment extends Fragment implements FavouritesAdapter.FavOrderListener {

    private FragmentDashboardBinding binding;
    private RecyclerView itemsRecyclerView;
    AppCompatButton checkoutButton;
    FavouritesAdapter homeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        checkoutButton = binding. buttonDel;
        itemsRecyclerView = binding.FavItemsRecyclerView;

        updateCheckoutButton();
        homeAdapter = new FavouritesAdapter(getContext(), CommonSingleton.shared().favItems, this);
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

        //Checkout button updated when returning to page from checkout
        updateCheckoutButton();
        homeAdapter.notifyDataSetChanged();
    }

    //Method used to decide whether to display/Hide checkout button
    public void updateCheckoutButton()
    {
        if (CommonSingleton.shared().currentOrder == null)
        {
            checkoutButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            if(CommonSingleton.shared().currentOrder.items.size() == 0)
            {
                checkoutButton.setVisibility(View.INVISIBLE);
            }
            else
            {
                checkoutButton.setVisibility(View.VISIBLE);
            }
        }
    }

    //Called whenever food item add/removed from list from adapter
    @Override
    public void currentOrderUpdated() {
        updateCheckoutButton();
    }
}