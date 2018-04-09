package com.example.stevenromp.plantsofci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.stevenromp.plantsofci.R;

/**
 * Created by tyler.allen925 on 3/7/18.
 */

public class CentralMap extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.central_map);
    }

    public void plantSearchButton(View view) {
        startActivity(new Intent(this, com.example.stevenromp.plantsofci.PlantSearch.class));
    }
}
