package com.example.admin.medhub;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 06-04-2017.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShoppingCartData> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView medicineName, price, manufacturerName;

        public ViewHolder(View view) {
            super(view);
            medicineName = (TextView) view.findViewById(R.id.tv_medName_cart);
            price = (TextView) view.findViewById(R.id.tv_price_cart);
            manufacturerName = (TextView) view.findViewById(R.id.tv_manufacturerName_cart);
        }
    }

    public CartAdapter() {
        this.list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_product_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ShoppingCartData item = list.get(position);
        if (holder instanceof CartAdapter.ViewHolder) {
            final CartAdapter.ViewHolder viewHolder = (CartAdapter.ViewHolder) holder;
            viewHolder.medicineName.setText(item.getName());
            viewHolder.price.setText(item.getPrice());
            viewHolder.manufacturerName.setText(item.getManufacturer());
        }

    }

    public void replaceAll(List<ShoppingCartData> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }
}