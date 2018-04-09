package com.example.stevenromp.plantsofci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        startActivity(new Intent(this, com.example.stevenromp.plantsofci.PlantSpecification.class));
    }
}
