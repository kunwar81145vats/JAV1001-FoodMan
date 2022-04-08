package com.example.foodman.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodman.HomeAdapter;
import com.example.foodman.R;
import com.example.foodman.databinding.FragmentDashboardBinding;
import com.example.foodman.ui.home.CommonSingleton;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView itemsRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        itemsRecyclerView = binding.FavItemsRecyclerView;

        FavouritesAdapter homeAdapter = new FavouritesAdapter(getActivity().getApplicationContext(), CommonSingleton.favItems);
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
}