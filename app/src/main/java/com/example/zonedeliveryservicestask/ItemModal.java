package com.example.zonedeliveryservicestask;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ItemModal implements Parcelable {
    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private String itemId;
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public ItemModal() {

    }
    protected ItemModal(Parcel in) {
        itemName = in.readString();
        itemId = in.readString();
        itemDescription = in.readString();
        itemPrice = in.readString();
    }

    public static final Creator<ItemModal> CREATOR = new Creator<ItemModal>() {
        @Override
        public ItemModal createFromParcel(Parcel in) {
            return new ItemModal(in);
        }

        @Override
        public ItemModal[] newArray(int size) {
            return new ItemModal[size];
        }
    };

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemDescription() {
        return itemDescription;
    }
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public String getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public ItemModal(String itemId, String itemName, String itemDescription, String itemPrice) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemId);
        dest.writeString(itemDescription);
        dest.writeString(itemPrice);
    }
}
