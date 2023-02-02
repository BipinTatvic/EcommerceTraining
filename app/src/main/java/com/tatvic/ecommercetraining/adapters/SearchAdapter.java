//package com.tatvic.ecommercetraining.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.tatvic.ecommercetraining.ProductDetail;
//import com.tatvic.ecommercetraining.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.ExampleViewHolder> implements Filterable {
//    //    private List<ItemModel> exampleList;
////    private List<ItemModel> exampleListFull;
//    private Context context;
//
//    class ExampleViewHolder extends RecyclerView.ViewHolder {
//        ImageView product_image;
//        TextView item_name;
//        TextView item_price;
//        Button addToCart;
//        CardView singleCardClick;
//
//        ExampleViewHolder(View itemView) {
//            super(itemView);
//            product_image = itemView.findViewById(R.id.product_image);
//            item_name = itemView.findViewById(R.id.item_name);
//            item_price = itemView.findViewById(R.id.item_price);
//            addToCart = itemView.findViewById(R.id.addToCart);
//            singleCardClick = itemView.findViewById(R.id.singleCardClick);
//        }
//    }
//
//    //    public SearchAdapter(Context context, List<ItemModel> exampleList) {
////        this.context = context;
////        this.exampleList = exampleList;
////        exampleListFull = new ArrayList<>(exampleList);
////    }
//    @NonNull
//    @Override
//    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plp, parent, false);
//        return new ExampleViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
////        ItemModel currentItem = exampleList.get(position);
////        holder.product_image.setImageResource(currentItem.getImgid());
////        holder.item_name.setText(currentItem.getItem_name());
////        holder.item_price.setText(currentItem.getItem_price());
////        holder.addToCart.setVisibility(View.GONE);
////        holder.singleCardClick.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(context, ProductDetail.class);
////                context.startActivity(intent);
////            }
////        });
//    }
//
//    @Override
//    public int getItemCount() {
//        //return exampleList.size();
//    }
////    @Override
//////    public Filter getFilter() {
//////        return exampleFilter;
//////    }
//////    private Filter exampleFilter = new Filter() {
//////        @Override
//////        protected FilterResults performFiltering(CharSequence constraint) {
//////            List<ItemModel> filteredList = new ArrayList<>();
//////            if (constraint == null || constraint.length() == 0) {
//////                filteredList.addAll(exampleListFull);
//////            } else {
//////                String filterPattern = constraint.toString().toLowerCase().trim();
//////                for (ItemModel item : exampleListFull) {
//////                    if (item.getItem_name().toLowerCase().contains(filterPattern)) {
//////                        filteredList.add(item);
//////                    }
//////                }
//////            }
//////            FilterResults results = new FilterResults();
//////            results.values = filteredList;
//////            return results;
//////        }
//////        @Override
//////        protected void publishResults(CharSequence constraint, FilterResults results) {
//////            exampleList.clear();
//////            exampleList.addAll((List) results.values);
//////            notifyDataSetChanged();
//////        }
//////    };
//}
//
