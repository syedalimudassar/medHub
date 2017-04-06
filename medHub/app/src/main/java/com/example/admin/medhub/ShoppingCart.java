package com.example.admin.medhub;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 04-04-2017.
 */

public class ShoppingCart extends BaseActivity {

    public DatabaseReference mDatabase;
    ArrayList<ShoppingCartData> list;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        list = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_shoppingCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter();
        recyclerView.setAdapter(cartAdapter);
        showToast("Loading Cart");
        showProgress();
        mDatabase.child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                manipulateData((HashMap<String, ShoppingCartData>) dataSnapshot.getValue());
                hideProgress();
                showToast("List updated...");
                cartAdapter.replaceAll(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void manipulateData(Map<String, ShoppingCartData> map) {
        list.clear();
        for (Map.Entry<String, ShoppingCartData> entry : map.entrySet()) {
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
            list.add(new ShoppingCartData(name, url, manufacturer, price, quantity, unit, type, dealerName, dealerID));
        }
    }
}