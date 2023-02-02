package com.tatvic.ecommercetraining.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvic.ecommercetraining.Cart;
import com.tatvic.ecommercetraining.ProductDetail;
import com.tatvic.ecommercetraining.R;
import com.tatvic.ecommercetraining.model.ItemModel;

import java.util.ArrayList;

public class PLPAdapter extends RecyclerView.Adapter<PLPAdapter.ViewHolder> {

    Context context;
    ArrayList<ItemModel> product_list;
    ArrayList<ItemModel> exampleList;

    public PLPAdapter(Context context, ArrayList<ItemModel> product_list) {
        this.context = context;
        this.product_list = product_list;
    }

    @NonNull
    @Override
    public PLPAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View product_list_item = layoutInflater.inflate(R.layout.item_plp, parent, false);
        ViewHolder viewHolder = new ViewHolder(product_list_item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PLPAdapter.ViewHolder holder, int position) {
        final ItemModel itemModel = product_list.get(position);
        holder.item_name.setText("Product Name: " + itemModel.getItem_name());
        holder.item_price.setText("Product Price: " + itemModel.getItem_price());
        holder.item_img.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), itemModel.getImgid(), null));
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addToCart.setText("View Cart");
                holder.addToCart.setBackgroundColor(0x20676470);
                Intent intent = new Intent(context, Cart.class);
                context.startActivity(intent);
            }
        });
        holder.singleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProductDetail.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

//    @Override
//    public Filter getFilter() {
//        return exampleFilter;
//    }
//    private Filter exampleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<ItemModel> filteredList = new ArrayList<>();
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(filteredList);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
////                for (product_list item : exampleList) {
////                    if (item.item_name.toLowerCase().contains(filterPattern)) {
////                        filteredList.add(item);
////                    }
////                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
////        }
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            exampleList.clear();
//            exampleList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//    };

    public class ViewHolder  extends RecyclerView.ViewHolder{

        public TextView item_name;
        public TextView item_price;
        public ImageView item_img;
        public Button addToCart;
        public CardView singleCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            item_img = itemView.findViewById(R.id.product_image);
            addToCart = itemView.findViewById(R.id.addToCart);
            singleCard = itemView.findViewById(R.id.singleCardClick);

        }
    }
}
