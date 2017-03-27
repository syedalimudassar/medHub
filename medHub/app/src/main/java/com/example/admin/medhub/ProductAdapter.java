package com.example.admin.medhub;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 27-03-2017.
 */

public class ProductAdapter extends FirebaseRecyclerAdapter<ProductAdapter.ViewHolder, Data> {

    private List<Data> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView medName, price, manName;


        public ViewHolder(View view) {
            super(view);
            medName = (TextView) view.findViewById(R.id.tv_medName);
            price = (TextView) view.findViewById(R.id.tv_price);
            manName = (TextView) view.findViewById(R.id.tv_manufacturerName);

        }
    }

    public ProductAdapter(Query query, @Nullable ArrayList<Data> items,
                          @Nullable ArrayList<String> keys) {
        super(query, items, keys);
    }



    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customize, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        Data item = getItem(position);
        holder.medName.setText(item.getStr_tradeName());
        holder.price.setText(item.getStr_price());
        holder.manName.setText(item.getStr_manufacturerName());
    }

    @Override
    protected void itemAdded(Data item, String key, int position) {
        Log.d("ProductAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Data oldItem, Data newItem, String key, int position) {
        Log.d("ProductAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Data item, String key, int position) {
        Log.d("ProductAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Data item, String key, int oldPosition, int newPosition) {
        Log.d("ProductAdapter", "Moved an item.");
    }

    public void replaceAll(List<Data> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }
}