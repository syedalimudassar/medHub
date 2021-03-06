package com.example.admin.medhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 26-03-2017.
 */

public class ProductListingActivity extends BaseActivity {

    int count=0;

    public DatabaseReference mDatabase;
    ArrayList<Data> list;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlisting);

        TextView tv_count= (TextView) findViewById(R.id.tv_count);

        ImageView cart= (ImageView) findViewById(R.id.imageViewCart);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), ShoppingCart.class);
                startActivity(intent);
            }
        });

        count= ProductAdapter.getCount();
        tv_count.setText(Integer.toString(count));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("cart").removeValue();

        list = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter();
        recyclerView.setAdapter(productAdapter);
        showToast("Fetching medicine list");
        showProgress();
        mDatabase.child("medicines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                manipulateData((HashMap<String, Data>) dataSnapshot.getValue());
                hideProgress();
                showToast("List updated...");
                productAdapter.replaceAll(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void manipulateData(Map<String, Data> map) {
        list.clear();
        for (Map.Entry<String, Data> entry : map.entrySet()) {
            String name = null;
            String manufacturer = null;
            String price = null;
            String quantity = null;
            String unit = null;
            String url = "";
            String type= null;
            String dealerName= null;
            String dealerID= null;
            Map<String, String> medicineMap = (Map<String, String>) entry.getValue();
            for (Map.Entry<String, String> medEntry : medicineMap.entrySet()) {
                String value = String.valueOf(medEntry.getValue());
                switch (medEntry.getKey()) {
                    case "str_tradeName":
                        name = value;
                        break;
                    case "str_manufacturerName":
                        manufacturer = value;
                        break;
                    case "str_price":
                        price = value;
                        break;
                    case "str_quantity":
                        quantity = value;
                        break;
                    case "str_unit":
                        unit = value;
                        break;
                    case "url":
                        url = value;
                        break;
                }
            }
            list.add(new Data(name, url, manufacturer, price, quantity, unit, type, dealerName, dealerID));
        }
    }
}