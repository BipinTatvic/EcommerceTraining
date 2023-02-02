package com.tatvic.ecommercetraining.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatvic.ecommercetraining.ProductDetail;
import com.tatvic.ecommercetraining.R;
import com.tatvic.ecommercetraining.model.ProductModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PLPListAdapter extends RecyclerView.Adapter<PLPListAdapter.MyViewHolder> {

    private List<ProductModel> menuList;
    private final MenuListClickListener clickListener;
    private Context context;

    public PLPListAdapter(Context context, List<ProductModel> menuList, MenuListClickListener clickListener) {
        this.menuList = menuList;
        this.clickListener = clickListener;
        this.context = context;
    }
    public void updateData(List<ProductModel> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PLPListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.plp_single_item, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PLPListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Price: "+String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(menuList.get(position).getPrice())));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetail.class);
                intent.putExtra("item_name", menuList.get(position).getName());
                intent.putExtra("item_price", String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(menuList.get(position).getPrice())));
                intent.putExtra("item_img_url", menuList.get(position).getUrl());
                context.startActivity(intent);
            }
        });
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel menu  = menuList.get(position);
                menu.setTotalInCart(1);
                clickListener.onAddToCartClick(menu);
                holder.addMoreLayout.setVisibility(View.VISIBLE);
                holder.addToCartButton.setVisibility(View.GONE);
                holder.tvCount.setText(menu.getTotalInCart()+"");
            }
        });
        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel menu  = menuList.get(position);
                int total = menu.getTotalInCart();
                total--;
                if(total > 0 ) {
                    menu.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menu);
                    holder.tvCount.setText(total +"");
                } else {
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                    menu.setTotalInCart(total);
                    clickListener.onRemoveFromCartClick(menu);
                }
            }
        });

        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel menu  = menuList.get(position);
                int total = menu.getTotalInCart();
                total++;
                if(total <= 10 ) {
                    menu.setTotalInCart(total);
                    clickListener.onUpdateCartClick(menu);
                    holder.tvCount.setText(total +"");
                }
            }
        });

        Glide.with(holder.thumbImage)
                .load(menuList.get(position).getUrl())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        return menuList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView menuName;
        TextView  menuPrice;
        TextView  addToCartButton;
        ImageView thumbImage;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView  tvCount;
        LinearLayout addMoreLayout;
        CardView cardView;

        public MyViewHolder(@NonNull View view) {
            super(view);

            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            addToCartButton = view.findViewById(R.id.addToCartButton);
            thumbImage = view.findViewById(R.id.thumbImage);
            imageMinus = view.findViewById(R.id.imageMinus);
            imageAddOne = view.findViewById(R.id.imageAddOne);
            tvCount = view.findViewById(R.id.tvCount);
            cardView = view.findViewById(R.id.cardView);

            addMoreLayout  = view.findViewById(R.id.addMoreLayout);
        }
    }

    public interface MenuListClickListener {
        public void onAddToCartClick(ProductModel menu);
        public void onUpdateCartClick(ProductModel menu);
        public void onRemoveFromCartClick(ProductModel menu);
    }
}

