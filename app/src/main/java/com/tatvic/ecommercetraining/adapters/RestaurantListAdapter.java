package com.tatvic.ecommercetraining.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tatvic.ecommercetraining.R;
import com.tatvic.ecommercetraining.model.RestaurantModel;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder> {

    private List<RestaurantModel> restaurantModelList;
    private RestaurantListClickListener clickListener;

    public RestaurantListAdapter(List<RestaurantModel> restaurantModelList, RestaurantListClickListener clickListener) {
        this.restaurantModelList = restaurantModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<RestaurantModel> restaurantModelList) {
        this.restaurantModelList = restaurantModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.item_name.setText(restaurantModelList.get(position).getName());
//        holder.restaurantAddress.setText("Address: "+restaurantModelList.get(position).getAddress());
//        holder.restaurantHours.setText("Today's hours: " + restaurantModelList.get(position).getHours().getTodaysHours());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(restaurantModelList.get(position));
            }
        });
        Glide.with(holder.item_img)
                .load(restaurantModelList.get(position).getImage())
                .into(holder.item_img);

    }

    @Override
    public int getItemCount() {
        return restaurantModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  item_name;
        TextView  restaurantAddress;
        TextView  restaurantHours;
        ImageView item_img;

        public MyViewHolder(View view) {
            super(view);
            item_name = view.findViewById(R.id.item_name);
//            restaurantAddress = view.findViewById(R.id.restaurantAddress);
//            restaurantHours = view.findViewById(R.id.restaurantHours);
            item_img = view.findViewById(R.id.item_img);

        }
    }

    public interface RestaurantListClickListener {
        public void onItemClick(RestaurantModel restaurantModel);
    }
}
