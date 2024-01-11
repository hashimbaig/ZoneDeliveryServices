package com.example.zonedeliveryservicestask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.zonedeliveryservicestask.R;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemClickInterface{

    private FloatingActionButton floatingActionButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ArrayList<ItemModal> itemModalArrayList;
    private ItemAdapter itemAdapter;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.Rvitem);
        relativeLayout = findViewById(R.id.RLmain);
        progressBar = findViewById(R.id.ProgressBar);
        floatingActionButton = findViewById(R.id.BtnAddItem);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        itemModalArrayList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference("Items");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Dashboard.class);
                startActivity(i);
            }
        });

        itemAdapter = new ItemAdapter(itemModalArrayList, this, this::onitemClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getItems();
    }

    public void getItems(){
        itemModalArrayList.clear();
        progressBar.setVisibility(View.GONE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.removeEventListener(this);
                progressBar.setVisibility(View.GONE);
                for(DataSnapshot item: snapshot.getChildren()){
                    itemModalArrayList.add(item.getValue(ItemModal.class));
                }
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                databaseReference.removeEventListener(this);
            }
        });
    }
    @Override
    public void onitemClick(int position) {
       displayBottomSheet(itemModalArrayList.get(position));
    }

    private void displayBottomSheet(ItemModal modal) {
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null);
        bottomSheetTeachersDialog.setContentView(layout);
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.show();
        TextView itemName = layout.findViewById(R.id.itemName);
        TextView itemDesc = layout.findViewById(R.id.itemDesc);
        TextView itemPrice = layout.findViewById(R.id.itemPrice);
        itemName.setText(modal.getItemName());
        itemDesc.setText(modal.getItemDescription());
        itemPrice.setText("Rs." + modal.getItemPrice());
        Button editBtn = layout.findViewById(R.id.BtnEditItem);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetTeachersDialog.dismiss();
                Intent i = new Intent(MainActivity.this, ItemEdit.class);
                i.putExtra("item",modal);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.logout){
            Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Intent i = new Intent(MainActivity.this, SignIn.class);
            startActivity(i);
            this.finish();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}