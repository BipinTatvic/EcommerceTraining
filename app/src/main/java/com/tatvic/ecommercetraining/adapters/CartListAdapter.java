package com.tatvic.ecommercetraining.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.tatvic.ecommercetraining.R;
import com.tatvic.ecommercetraining.model.ProductModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    private List<ProductModel> menuList;

    public CartListAdapter(List<ProductModel> menuList) {
        this.menuList = menuList;
    }

    public void updateData(List<ProductModel> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_single_item, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Price: " + String.format(String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(menuList.get(position).getPrice()*menuList.get(position).getTotalInCart()))));
        holder.menuQty.setText("Qty: " + menuList.get(position).getTotalInCart());
        Glide.with(holder.thumbImage)
                .load(menuList.get(position).getUrl())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  menuName;
        TextView  menuPrice;
        TextView  menuQty;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            menuQty = view.findViewById(R.id.menuQty);
            thumbImage = view.findViewById(R.id.thumbImage);
        }
    }
}
