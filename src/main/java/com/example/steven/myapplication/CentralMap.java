package com.example.steven.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        startActivity(new Intent(this, PlantSearch.class));
    }
}
