package com.tatvic.ecommercetraining.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel implements Parcelable {
    private String name;
    private float price;
    private int totalInCart;
    private String url;
    private String item_id;
    private String brand;
    private String variant;
    private String item_category;

    public ProductModel() {
    }

    public ProductModel(String name, float price, int totalInCart, String url, String item_id, String brand, String variant, String item_category) {
        this.name = name;
        this.price = price;
        this.totalInCart = totalInCart;
        this.url = url;
        this.item_id = item_id;
        this.brand = brand;
        this.variant = variant;
        this.item_category = item_category;
    }

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    public ProductModel(String name, float price, int totalInCart, String url) {
        this.name = name;
        this.price = price;
        this.totalInCart = totalInCart;
        this.url = url;
    }

    public ProductModel(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        url = in.readString();
        item_id = in.readString();
        brand = in.readString();
        variant = in.readString();
        totalInCart = in.readInt();
        item_category = in.readString();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVariant() {
        return variant;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(url);
        dest.writeString(item_id);
        dest.writeString(brand);
        dest.writeString(variant);
        dest.writeInt(totalInCart);
        dest.writeString(item_category);
    }
}

