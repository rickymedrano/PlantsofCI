package com.example.steven.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    private List<XMLParser.Entry> plantItems;
    Context mContext;

    public DataItemAdapter(Context context, List<XMLParser.Entry> items) {
        this.plantItems = items;
        mContext = context;
    }
    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataItemAdapter.ViewHolder holder, int position) {
        //get current Entry
        final XMLParser.Entry item = plantItems.get(position);

        //Set name and icon
        holder.tvName.setText(item.getCommonName().getObj());
        Picasso.get().load("file:///android_asset/PlantIcons/" + Integer.toString(position+1) + "_2_1.png").placeholder(R.drawable.cast_album_art_placeholder).into(holder.imageView);
        //holder.imageView.setImageResource(R.drawable.african_tulip_tree_icon);

        //Go to prospective plant page based on plantID
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemID = item.getPlantID().getObj();
                Intent intent = new Intent(mContext, PlantSpecification.class);
                intent.putExtra("plantID", itemID);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView imageView;
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            mView = itemView;
        }
    }
}