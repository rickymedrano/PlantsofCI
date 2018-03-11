package com.example.steven.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    DatabaseHelperClass myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        myDB = new DatabaseHelperClass(this);
    }
    
    public void interactiveMapButton(View view) {
        startActivity(new Intent(this, InteractiveMap.class));
    }

    public void plantSearchButton(View view) {
        startActivity(new Intent(this, PlantSearch.class));
    }

    public void aboutButton(View view){
        startActivity(new Intent(this, AboutPage.class));
    }
}
