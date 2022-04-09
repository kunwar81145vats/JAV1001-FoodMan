package com.example.foodman.ui.notifications;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodman.R;
import com.example.foodman.ui.dashboard.FavouritesAdapter;
import com.example.foodman.ui.home.CommonSingleton;
import com.example.foodman.ui.home.ItemModel;
import com.example.foodman.ui.home.OrderModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class OrdersAdapter  extends RecyclerView.Adapter<OrdersAdapter.Viewholder>   {

    private Context context;
    private ArrayList<OrderModel> OrderModelArrayList;

    // Constructor
    public OrdersAdapter(Context context, ArrayList<OrderModel> OrderModelArrayList) {
        this.context = context;
        this.OrderModelArrayList = OrderModelArrayList;
    }

    @NonNull
    @Override
    public OrdersAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_card_view, parent, false);
        return new OrdersAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout

        OrderModel order = OrderModelArrayList.get(position);

        String orderNumber = "Order #" + String.valueOf(order.id);
        holder.orderNumberText.setText(orderNumber);

        String orderList = "";
        for (ItemModel obj : order.items)
        {
            orderList += obj.name + "\n";
        }
        holder.itemListText.setText(orderList);

        holder.orderdelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /// button click event

                CommonSingleton.shared().pastOrders.remove(position);
                CommonSingleton.shared().savePastOrder();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return OrderModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView orderNumberText, itemListText;
        private AppCompatImageButton orderdelete;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            orderNumberText = itemView.findViewById(R.id.orderNumber);
            itemListText = itemView.findViewById(R.id.itemsList);
            orderdelete = itemView.findViewById(R.id.orderDelete); }
    }
}
