package com.example.steven.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    private List<PlantItem> plantItems;
    Context mContext;

    public DataItemAdapter(Context context, List<PlantItem> items) {
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
        final PlantItem item = plantItems.get(position);

        //attempt to set icon image from resource assets
        //assumes item.getPhotoID is valid
        try {
            String imageFile = item.getPhotoID();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.tvName.setText(item.getItemName());
        //holder.tvName.setText(database.getFullDatabase().get(position).getCommonName().getObj());
        //String imageFile = item.getImage();
        //holder.imageView.setImageResource(R.drawable.african_tulip_tree_icon);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemID = item.getItemId();
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