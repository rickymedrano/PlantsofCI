package com.example.steven.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PlantSpecification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_specification);

        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        Picasso.get().load("https://docs.google.com/uc?id=1_NroIB9reMuUEJyxPVDN7ZjR4qG5DALO").into(imageView);
    }
}
