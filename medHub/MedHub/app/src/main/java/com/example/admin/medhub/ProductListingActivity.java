package com.example.admin.medhub;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by Admin on 26-03-2017.
 */

public class ProductListingActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    Context context;
    ArrayList<Data> list = new ArrayList<>();
    public TextView medName, price, manName;
    public String str_tradeName, str_price, str_manufacturerName, str_unit, str_quantity, str_type;
    private RecyclerView recyclerView;
    ImageView imageView;
    Data[] recordses;
    ProductAdapter productAdapter;
    private static final String TAG = ProductListingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlisting);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mDatabase = FirebaseDatabase.getInstance().getReference("medicines");

        FirebaseDatabase.getInstance().getReference("app_title").setValue("MedHub Database");

        FirebaseDatabase.getInstance().getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read app title value.", databaseError.toException());
            }
        });

        medName = (TextView) findViewById(R.id.tv_medName);
        price = (TextView) findViewById(R.id.tv_price);
        manName = (TextView) findViewById(R.id.tv_manufacturerName);
        /*imageView= (ImageView) findViewById(R.id.imageView);*/

        productAdapter = new ProductAdapter(this, recordses);

        mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    Data dataMedicine = postSnapshot.getValue(Data.class);
                    list.add(dataMedicine);
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.hero);

                productAdapter = new ProductAdapter(list);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator()); //optional
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}