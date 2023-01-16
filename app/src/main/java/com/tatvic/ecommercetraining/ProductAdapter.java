package com.tatvic.ecommercetraining;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ProductAdapter extends ArrayAdapter<ItemModel> {
    public ProductAdapter(@NonNull Context context, ArrayList<ItemModel> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }

        ItemModel itemModel = getItem(position);
        TextView item_name = listitemView.findViewById(R.id.item_name);
        TextView item_price = listitemView.findViewById(R.id.item_price);
        ImageView item_img = listitemView.findViewById(R.id.item_img);

        item_name.setText(itemModel.getItem_name());
        item_price.setText(itemModel.getItem_price());
        item_img.setImageResource(itemModel.getImgid());
        return listitemView;
    }


}
