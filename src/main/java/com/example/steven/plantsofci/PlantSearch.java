package com.example.steven.plantsofci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.steven.myapplication.R;
import com.example.steven.plantsofci.PlantSpecification;

/**
 * Created by Tyler on 3/5/2018.
 */

public class PlantSearch extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_list);
    }

    public void plantSpecificationButton(View view) {
        startActivity(new Intent(this, PlantSpecification.class));
    }
}
