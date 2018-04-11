package com.example.stevenromp.plantsofci;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.example.stevenromp.plantsofci.AboutPage;
import com.example.stevenromp.plantsofci.InteractiveMap;
import com.example.stevenromp.plantsofci.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private static final int ERROR_DIALOG_REQUEST = 9001; // used when requesting certain dialogue boxes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        //Create input stream for XML file and pass into the parser (rempace the Null in parser(null)s
        XMLParser test = new XMLParser();
        try {
            test.parse(null);//XML IN STREAM HERE);
        }catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void interactiveMapButton(View view) {

        // make sure google map services are working
        if(servicesOk())
        {
            startActivity(new Intent(this, InteractiveMap.class));
        }
    }

    public void plantSearchButton(View view) {
        startActivity(new Intent(this, com.example.stevenromp.plantsofci.PlantSearch.class));
    }

    public void aboutButton(View view){
        startActivity(new Intent(this, AboutPage.class));
    }

    // make sure the google map service is good to use
    public boolean servicesOk() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to mapping service", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}




