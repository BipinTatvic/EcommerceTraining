package com.tatvic.ecommercetraining.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvic.ecommercetraining.R;
import com.tatvic.ecommercetraining.model.ItemModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    Context context;
    ArrayList<ItemModel> product_list;
    private Integer quantity = 1;

    public CartAdapter(Context context, ArrayList<ItemModel> product_list) {
        this.context = context;
        this.product_list = product_list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View product_list_item = layoutInflater.inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(product_list_item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ItemModel itemModel = product_list.get(position);
        holder.item_name.setText("Product Name: " + itemModel.getItem_name());
        holder.item_price.setText("Product Price: " + itemModel.getItem_price());
        holder.item_img.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), itemModel.getImgid(), null));
        holder.add_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = quantity + 1;
                holder.txt_quantity.setText(quantity.toString());
            }
        });
        holder.remove_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(quantity == 1){
                    product_list.remove(position);
                    notifyDataSetChanged();
                }
                else {

                }
                quantity = quantity - 1;
                holder.txt_quantity.setText(quantity.toString());
            }
        });

        holder.remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_list.remove(position);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView item_name;
        public TextView item_price;
        public TextView txt_quantity;
        public ImageView item_img;
        public ImageButton add_quantity, remove_quantity, remove_from_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            item_img = itemView.findViewById(R.id.product_image);
            add_quantity = itemView.findViewById(R.id.add_quantity);
            remove_quantity = itemView.findViewById(R.id.remove_quantity);
            remove_from_cart = itemView.findViewById(R.id.remove_from_cart);
            txt_quantity = itemView.findViewById(R.id.txt_quantity);
        }
    }
}
