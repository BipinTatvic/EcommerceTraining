package com.tatvic.ecommercetraining.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatvic.ecommercetraining.ProductDetail;
import com.tatvic.ecommercetraining.R;
import com.tatvic.ecommercetraining.Search;
import com.tatvic.ecommercetraining.model.CategoryModel;
import com.tatvic.ecommercetraining.model.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ExampleViewHolder> implements Filterable {
    private List<CategoryModel> exampleList;
    private List<CategoryModel> exampleListFull;
    private RestaurantListClickListener clickListener;
    private Context context;


    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView item_name;
        TextView item_price;
        LinearLayout ItemLayout;

        ExampleViewHolder(View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.thumbImage);
            item_name = itemView.findViewById(R.id.menuName);
            item_price = itemView.findViewById(R.id.menuPrice);
            ItemLayout = itemView.findViewById(R.id.ItemLayout);
        }
    }

    public SearchAdapter(List<CategoryModel> exampleList, RestaurantListClickListener clickListener) {
        this.exampleList = exampleList;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_single_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModel currentItem = exampleList.get(position);
        Glide.with(holder.product_image)
                .load(currentItem.getImage())
                .into(holder.product_image);
        holder.item_name.setText(currentItem.getName());
        holder.item_price.setText("Delivery Charge: " + String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(exampleList.get(position).getDelivery_charge())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(exampleList.get(position));
            }
        });
/*        holder.ItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductDetail.class);
//                intent.putExtra("item_name", exampleList.get(position).getName());
//                intent.putExtra("item_price", Float.valueOf(exampleList.get(position).getDelivery_charge()));
//                intent.putExtra("item_img_url", exampleList.get(position).getImage());
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CategoryModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CategoryModel item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            Log.d("YOUR_SEARCH", String.valueOf(constraint) +"\n"+results.values);

//            exampleList.removeAll();
//            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface RestaurantListClickListener {
        public void onItemClick(CategoryModel categoryModel);
    }
}

