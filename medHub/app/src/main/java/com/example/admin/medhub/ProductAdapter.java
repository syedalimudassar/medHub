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
 * Created by smudassar on 7/27/2015.
 */

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Data> list;
    static int count=0;
    public DatabaseReference mDatabase;
    public String str_tradeName, str_price, str_manufacturerName, str_unit, str_count,
            str_quantity, str_type, str_dealerName, str_dealerID, str_url;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView medicineName, price, manufacturerName, quantity, tv_count;
        Button button_buy;

        public ViewHolder(View view) {
            super(view);
            medicineName = (TextView) view.findViewById(R.id.tv_medName);
            price = (TextView) view.findViewById(R.id.tv_price);
            manufacturerName = (TextView) view.findViewById(R.id.tv_manufacturerName);
            tv_count= (TextView) view.findViewById(R.id.tv_count);
            button_buy=(Button) view.findViewById(R.id.btn_buy);
        }
    }

    public ProductAdapter() {
        this.list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_products_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Data item = list.get(position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.medicineName.setText(item.getName());
            viewHolder.price.setText(item.getPrice());
            viewHolder.manufacturerName.setText(item.getManufacturer());
            viewHolder.button_buy.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    str_manufacturerName = viewHolder.manufacturerName.getText().toString();
                    str_price = viewHolder.price.getText().toString();
                    str_tradeName = viewHolder.medicineName.getText().toString();

                    count=count++;
                    str_count=Integer.toString(count);
                    Toast.makeText(v.getContext(), str_count, Toast.LENGTH_SHORT).show();

                    Data medData = new Data(str_tradeName, str_url, str_manufacturerName, str_price, str_quantity, str_unit, str_type, str_dealerName, str_dealerID);
                    mDatabase.child("cart").child(str_tradeName).setValue(medData);
                }
            });
        }

    }

    public void replaceAll(List<Data> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    public static int getCount(){
        count=count++;
        return count;
    }
}