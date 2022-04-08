package com.example.foodman.ui;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodman.HomeAdapter;
import com.example.foodman.R;
import com.example.foodman.ui.home.CommonSingleton;
import com.example.foodman.ui.home.ItemModel;
import com.example.foodman.ui.home.OrderModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private ArrayList<ItemModel> itemModelArrayList;
    CheckoutListener mListener;

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_ITEM = 1;

    // Constructor
    public CheckoutAdapter(Context context, ArrayList<ItemModel> itemModelArrayList, CheckoutListener mListener) {
        this.context = context;
        this.itemModelArrayList = itemModelArrayList;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        if (viewType == TYPE_FOOTER)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_footer_view, parent, false);
            return new FooterViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_item_card_view, parent, false);
            return new ItemViewholder(view, mListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // to set data to textview and imageview of each card layout

        if (holder instanceof FooterViewHolder)
        {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            String totalCount = String.valueOf(CommonSingleton.currentOrder.items.size());
            footerHolder.footerText.setText(totalCount);
        }
        else
        {
            ItemViewholder itemHolder = (ItemViewholder) holder;

            ItemModel model = itemModelArrayList.get(position);
            itemHolder.itemName.setText(model.name);

            itemHolder.updateQuantityButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    /// button click event
                    int orderId = 1;


                    int itemIndex = -1;
                    for (int i = 0; i < CommonSingleton.currentOrder.items.size(); i++)
                    {
                        if (CommonSingleton.currentOrder.items.get(i).id == model.id)
                        {
                            itemIndex = i;
                            break;
                        }
                    }

                    CommonSingleton.currentOrder.items.remove(itemIndex);

                    if(CommonSingleton.currentOrder.items.size() == 0)
                    {
                        mListener.currentOrderUpdated();
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return itemModelArrayList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == itemModelArrayList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public interface CheckoutListener
    {
        void currentOrderUpdated();
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerText;

        public FooterViewHolder(View view) {
            super(view);
            footerText = (TextView) view.findViewById(R.id.totalCountText);
        }
    }

    // View holder class for initializing of
    public class ItemViewholder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private AppCompatButton updateQuantityButton;
        CheckoutListener mListener;

        public ItemViewholder(View itemView, CheckoutListener mListener) {
            super(itemView);
            this.mListener = mListener;
            itemName = itemView.findViewById(R.id.itemName);
            updateQuantityButton = itemView.findViewById(R.id.updateItemButton);
        }
    }
}
