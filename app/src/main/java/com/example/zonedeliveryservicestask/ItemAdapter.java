package com.example.zonedeliveryservicestask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private ArrayList<ItemModal>itemModalArrayList;
    private Context context;
    private ItemClickInterface itemClickInterface;
    int lastPos = -1;

    public ItemAdapter(ArrayList<ItemModal> itemModalArrayList, Context context, ItemClickInterface itemClickInterface) {
        this.itemModalArrayList = itemModalArrayList;
        this.context = context;
        this.itemClickInterface = itemClickInterface;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        ItemModal itemAdapter = itemModalArrayList.get(position);
        holder.itemName.setText(itemAdapter.getItemName());
        holder.itemDescription.setText(itemAdapter.getItemDescription());
        holder.itemPrice.setText("Rs. " + itemAdapter.getItemPrice());
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View itemView, int position) {

        if (position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return itemModalArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView itemName,itemDescription, itemPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickInterface != null){
                itemClickInterface.onitemClick(getAdapterPosition());
            }
        }
    }
    public interface ItemClickInterface {
        void onitemClick(int position);
    }
}
