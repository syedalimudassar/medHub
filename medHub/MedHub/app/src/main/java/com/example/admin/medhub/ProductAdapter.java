package com.example.admin.medhub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by smudassar on 7/27/2015.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Sam> {
    private Context context;
    private Data[] recordses;
    private ArrayList<Data> list = new ArrayList<>();
    private Object[] obj;

    public ProductAdapter(Context context, Data[] recordses) {

        this.context = context;
        this.recordses = recordses;
    }

    public ProductAdapter(ArrayList<Data> list) {
        this.list = list;
        obj = list.toArray();
    }

    @Override
    public Sam onCreateViewHolder(ViewGroup parent, int viewType) {

        View values = LayoutInflater.from(parent.getContext()).inflate(R.layout.customize, null);
        Sam sam = new Sam(values);
        return sam;
    }

    @Override
    public void onBindViewHolder(Sam holder, int position) {
        /*holder.medName.setText(recordses[position].getName());
        holder.price.setText(recordses[position].getEmail());*/
        holder.medName.setText(obj[position].toString());
        /*holder.image_Id.setImageResource(recordses[position].getImage_id());*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Sam extends RecyclerView.ViewHolder {

        TextView medName;
        TextView price;
        TextView manName;
        /*ImageView image_Id;*/

        public Sam(View itemView) {
            super(itemView);
            medName = (TextView) itemView.findViewById(R.id.tv_medName);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            manName = (TextView) itemView.findViewById(R.id.tv_manufacturerName);
            /*image_Id = (ImageView) itemView.findViewById(R.id.image);*/
        }
    }
}