package com.example.admin.medhub;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class ProductListingActivity extends Activity {

    public DatabaseReference mDatabase;
    Context context;
    Data[] records;
    ArrayList<Data> list = new ArrayList<>();
    public TextView medName, price, manName;
    public String str_tradeName, str_price, str_manufacturerName, str_unit, str_quantity, str_type;
    private RecyclerView recyclerView;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ImageView imageView;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlisting);

        this.context = this;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        medName = (TextView) findViewById(R.id.tv_medName);
        price = (TextView) findViewById(R.id.tv_price);
        manName = (TextView) findViewById(R.id.tv_manufacturerName);
        /*imageView= (ImageView) findViewById(R.id.imageView);*/

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    Data dataMedicine = postSnapshot.getValue(Data.class);

                    //Adding it to a string
                    str_manufacturerName = (String) postSnapshot.child("str_manufacturerName").getValue();
                    str_price = (String) postSnapshot.child("str_price").getValue();
                    str_quantity = (String) postSnapshot.child("str_quantity").getValue();
                    str_tradeName = (String) postSnapshot.child("str_tradeName").getValue();
                    str_type = (String) postSnapshot.child("str_type").getValue();
                    str_unit = (String) postSnapshot.child("str_unit").getValue();

                    records = new Data[]{new Data(str_tradeName, str_price, str_manufacturerName, str_unit, str_quantity, str_type)};

                    list.add(dataMedicine);

                   /* Glide.with(context)
                            .using(new FirebaseImageLoader())
                            .load(storageRef)
                            .into(imageView);*/
                }
//                ProductAdapter.replaceAll();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.hero);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        productAdapter = new ProductAdapter(null,ite, records);
        recyclerView.setAdapter(productAdapter);
    }
}