package com.example.zonedeliveryservicestask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class Dashboard extends AppCompatActivity {

    private TextInputEditText itemName, itemDescription, itemPrice;
    private Button btnAddItem;
    private ProgressBar progressBar;
    private String itemID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        itemName = findViewById(R.id.EnterItemName);
        itemDescription = findViewById(R.id.EnterItemDescription);
        itemPrice = findViewById(R.id.EnterItemPrice);
        btnAddItem = findViewById(R.id.btnAddItem);
        progressBar = findViewById(R.id.ProgressBar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Items");

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String nameItem = itemName.getText().toString();
                String descriptionItem = itemDescription.getText().toString();
                String priceItem = itemPrice.getText().toString();
                itemID = nameItem;

                ItemModal itemModal = new ItemModal(itemID, nameItem, descriptionItem, priceItem);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.removeEventListener(this);
                        databaseReference.child(itemID).setValue(itemModal);
                        Toast.makeText(Dashboard.this, "Item Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        databaseReference.removeEventListener(this);
                        Toast.makeText(Dashboard.this, "Item not added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}