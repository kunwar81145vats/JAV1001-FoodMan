package com.example.foodman.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodman.HomeAdapter;
import com.example.foodman.R;
import com.example.foodman.databinding.FragmentNotificationsBinding;
import com.example.foodman.ui.home.CommonSingleton;
import com.example.foodman.ui.home.OrderModel;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView itemsRecyclerView = binding.ItemsRecyclerView;

        OrdersAdapter adapter = new OrdersAdapter(getContext(), CommonSingleton.shared().pastOrders);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        itemsRecyclerView.setLayoutManager(linearLayoutManager);
        itemsRecyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}