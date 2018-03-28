package com.example.steven.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Tyler on 3/5/2018.
 */

public class InteractiveMap extends Activity
{
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactive_map);
    }
}
