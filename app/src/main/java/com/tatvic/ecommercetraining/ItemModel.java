package com.tatvic.ecommercetraining;

import androidx.annotation.DrawableRes;

public class ItemModel {

    private String item_name;
    private String item_price;

    @DrawableRes
    private int imgid;

    public ItemModel(String item_name, String item_price, int imgid) {
        this.item_name = item_name;
        this.item_price = item_price;
        this.imgid = imgid;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}

