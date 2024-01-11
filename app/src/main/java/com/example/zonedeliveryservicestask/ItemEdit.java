package com.example.zonedeliveryservicestask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemEdit extends AppCompatActivity {

    private TextInputEditText itemNameEdt, itemDescEdt, itemPriceEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ItemModal itemModal;
    private ProgressBar progressBar;
    private String itemID;
    private Button btnAddItem, btnDellItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);
        itemNameEdt = findViewById(R.id.EdtitemName);
        itemDescEdt = findViewById(R.id.EdtItemDescription);
        itemPriceEdt = findViewById(R.id.EdtItemPrice);
        progressBar = findViewById(R.id.progressBar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        btnAddItem = findViewById(R.id.BtnAddItem);
        btnDellItem = findViewById(R.id.BtnDeleteItem);
        itemModal = getIntent().getParcelableExtra("item");

        if (itemModal != null) {
            itemNameEdt.setText(itemModal.getItemName());
            itemDescEdt.setText(itemModal.getItemDescription());
            itemPriceEdt.setText(itemModal.getItemPrice());
            itemID = itemModal.getItemId();
        }
        databaseReference = firebaseDatabase.getReference("Items").child(itemID);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String itemName = itemNameEdt.getText().toString();
                String itemDescription = itemDescEdt.getText().toString();
                String itemPrice = itemPriceEdt.getText().toString();
                Map<String, Object> map = new HashMap<>();
                map.put("itemName", itemName);
                map.put("itemDescription", itemDescription);
                map.put("itemPrice", itemPrice);
                map.put("itemID", itemID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.removeEventListener(this);
                        progressBar.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(ItemEdit.this, "ItemUpdated", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        databaseReference.removeEventListener(this);
                        Toast.makeText(ItemEdit.this, "Item Not Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnDellItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });
    }
    private void deleteItem() {
        databaseReference.removeValue();
        Toast.makeText(this, "Item Deleted..", Toast.LENGTH_SHORT).show();
        finish();
    }
}