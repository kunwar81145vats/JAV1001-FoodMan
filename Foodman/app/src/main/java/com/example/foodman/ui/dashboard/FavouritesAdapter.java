package com.example.foodman.ui.dashboard;

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

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.Viewholder>  {

    private Context context;
    private ArrayList<ItemModel> itemModelArrayList;
    FavOrderListener mListener;

    // Constructor
    public FavouritesAdapter(Context context, ArrayList<ItemModel> itemModelArrayList, FavOrderListener mListener) {
        this.context = context;
        this.itemModelArrayList = itemModelArrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public FavouritesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card_view, parent, false);
        return new FavouritesAdapter.Viewholder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesAdapter.Viewholder holder, int position) {
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

            boolean isFav = false;
            for (ItemModel obj : CommonSingleton.shared().favItems)
            {
                if (obj.id == model.id)
                {
                    isFav = true;
                    break;
                }
            }
            if (isFav)
            {
                holder.favButton.setImageResource(R.drawable.fav_marked);
            }
            else
            {
                holder.favButton.setImageResource(R.drawable.heart);
            }

            if (CommonSingleton.shared().currentOrder == null)
            {
                holder.updateQuantityButton.setText("Add");
            }
            else
            {
                boolean found = false;
                for (ItemModel obj : CommonSingleton.shared().currentOrder.items)
                {
                    if (obj.id == model.id)
                    {
                        found = true;
                        break;
                    }
                }
                if (found)
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
                    for (int i = 0; i < CommonSingleton.shared().favItems.size(); i++)
                    {
                        if (CommonSingleton.shared().favItems.get(i).id == model.id)
                        {
                            itemIndex = i;
                            break;
                        }
                    }

                    if (itemIndex != -1)
                    {
                        CommonSingleton.shared().favItems.remove(itemIndex);
                    }
                    else
                    {
                        CommonSingleton.shared().favItems.add(model);
                    }

                    CommonSingleton.shared().saveFavourites();
                    notifyDataSetChanged();
                }
            });

            holder.updateQuantityButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    /// button click event
                    int orderId = 1;

                    if (CommonSingleton.shared().currentOrder == null)
                    {
                        CommonSingleton.shared().currentOrder = new OrderModel(orderId);
                        CommonSingleton.shared().currentOrder.items = new ArrayList<>();
                        CommonSingleton.shared().currentOrder.items.add(model);
                    }
                    else
                    {
                        int itemIndex = -1;
                        for (int i = 0; i < CommonSingleton.shared().currentOrder.items.size(); i++)
                        {
                            if (CommonSingleton.shared().currentOrder.items.get(i).id == model.id)
                            {
                                itemIndex = i;
                                break;
                            }
                        }

                        if (itemIndex == -1)
                        {
                            ItemModel item = model;
                            CommonSingleton.shared().currentOrder.items.add(item);
                        }
                        else
                        {
                            CommonSingleton.shared().currentOrder.items.remove(itemIndex);
                        }
                    }

                    CommonSingleton.shared().saveCurrentOrder();
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
        FavOrderListener mListener;

        public Viewholder(@NonNull View itemView, FavOrderListener mListener) {
            super(itemView);
            this.mListener = mListener;
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemName = itemView.findViewById(R.id.itemName);
            itemDescription = itemView.findViewById(R.id.itemDesc);
            updateQuantityButton = itemView.findViewById(R.id.updateItemButton);
            favButton = itemView.findViewById(R.id.favButton);
        }
    }

    public interface FavOrderListener
    {
        void currentOrderUpdated();
    }
}
