package com.example.foodman;

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

import com.example.foodman.ui.home.CommonSingleton;
import com.example.foodman.ui.home.ItemModel;
import com.example.foodman.ui.home.OrderModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Viewholder>  {

    CurrentOrderListener mListener;
    private Context context;
    private ArrayList<ItemModel> itemModelArrayList;

    // Constructor
    public HomeAdapter(Context context, ArrayList<ItemModel> itemModelArrayList, CurrentOrderListener mListener) {
        this.context = context;
        this.itemModelArrayList = itemModelArrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public HomeAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card_view, parent, false);
        return new Viewholder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout

        if (position < itemModelArrayList.size())
        {
            ItemModel model = itemModelArrayList.get(position);
            holder.itemName.setText(model.name);
            holder.itemDescription.setText(model.description);

            try {
                // get input stream
                AssetManager assetManager = context.getAssets();

                InputStream ims = assetManager.open(model.img);
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                holder.itemImageView.setImageDrawable(d);
            }
            catch(IOException ex) {
                return;
            }

            if (CommonSingleton.favItems.contains(model))
            {
                holder.favButton.setImageResource(R.drawable.fav_marked);
            }
            else
            {
                holder.favButton.setImageResource(R.drawable.heart);
            }

            if (CommonSingleton.currentOrder == null)
            {
                holder.updateQuantityButton.setText("Add");
            }
            else
            {
                if (CommonSingleton.currentOrder.items.contains(model))
                {
                    holder.updateQuantityButton.setText("Remove");
                }
                else
                {
                    holder.updateQuantityButton.setText("Add");
                }
            }

            holder.favButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    /// button click event
                    int itemIndex = -1;
                    for (int i = 0; i < CommonSingleton.favItems.size(); i++)
                    {
                        if (CommonSingleton.favItems.get(i).id == model.id)
                        {
                            itemIndex = i;
                            break;
                        }
                    }

                    if (itemIndex != -1)
                    {
                        CommonSingleton.favItems.remove(itemIndex);
                    }
                    else
                    {
                        CommonSingleton.favItems.add(model);
                    }

                    notifyDataSetChanged();
                }
            });

            holder.updateQuantityButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    /// button click event
                    int orderId = 1;

                    if (CommonSingleton.currentOrder == null)
                    {
                        CommonSingleton.currentOrder = new OrderModel(orderId);
                        CommonSingleton.currentOrder.items = new ArrayList<>();
                        CommonSingleton.currentOrder.items.add(model);
                    }
                    else
                    {
                        int itemIndex = -1;
                        for (int i = 0; i < CommonSingleton.currentOrder.items.size(); i++)
                        {
                            if (CommonSingleton.currentOrder.items.get(i).id == model.id)
                            {
                                itemIndex = i;
                                break;
                            }
                        }

                        if (itemIndex == -1)
                        {
                            ItemModel item = model;
                            item.quantity = 1;
                            CommonSingleton.currentOrder.items.add(item);
                        }
                        else
                        {
                            CommonSingleton.currentOrder.items.remove(itemIndex);
                        }
                    }

                    mListener.currentOrderUpdated();
                    notifyDataSetChanged();
                }
            });

            holder.favButton.setVisibility(View.VISIBLE);
            holder.itemDescription.setVisibility(View.VISIBLE);
            holder.itemImageView.setVisibility(View.VISIBLE);
            holder.itemName.setVisibility(View.VISIBLE);
            holder.updateQuantityButton.setVisibility(View.VISIBLE);
            holder.itemView.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.favButton.setVisibility(View.INVISIBLE);
            holder.itemDescription.setVisibility(View.INVISIBLE);
            holder.itemImageView.setVisibility(View.INVISIBLE);
            holder.itemName.setVisibility(View.INVISIBLE);
            holder.updateQuantityButton.setVisibility(View.INVISIBLE);
            holder.itemView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return itemModelArrayList.size() + 1;
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView itemImageView;
        private TextView itemName, itemDescription;
        private AppCompatButton updateQuantityButton;
        private ImageButton favButton;
        CurrentOrderListener mListener;

        public Viewholder(@NonNull View itemView, CurrentOrderListener mListener) {
            super(itemView);
            this.mListener = mListener;
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemName = itemView.findViewById(R.id.itemName);
            itemDescription = itemView.findViewById(R.id.itemDesc);
            updateQuantityButton = itemView.findViewById(R.id.updateItemButton);
            favButton = itemView.findViewById(R.id.favButton);
        }
    }

    public interface CurrentOrderListener
    {
        void currentOrderUpdated();
    }
}
